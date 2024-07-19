package com.luizmedeirosn.futs3.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerPictureRepository;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterIdScoreDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PlayerParameterDataDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PlayerMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import com.luizmedeirosn.futs3.shared.exceptions.PageableException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class PlayerService {

  private final PlayerRepository playerRepository;
  private final PlayerPictureRepository playerPictureRepository;
  private final PlayerParameterRepository playerParameterRepository;
  private final PositionRepository positionRepository;
  private final ObjectMapper objectMapper;
  private final EntityManager entityManager;

  public PlayerService(
      PlayerRepository playerRepository,
      PlayerPictureRepository playerPictureRepository,
      PlayerParameterRepository playerParameterRepository,
      PositionRepository positionRepository,
      ObjectMapper objectMapper,
      EntityManager entityManager) {
    this.playerRepository = playerRepository;
    this.playerPictureRepository = playerPictureRepository;
    this.playerParameterRepository = playerParameterRepository;
    this.positionRepository = positionRepository;
    this.objectMapper = objectMapper;
    this.entityManager = entityManager;
  }

  public static String createPictureUrl(Long id) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .replacePath("/players/picture/" + id)
        .replaceQuery(null)
        .toUriString();
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Page<PlayerMinResponseDTO> findAll(String keyword, Pageable pageable) {
    if (pageable.getPageSize() > 30) {
      throw new PageableException("The maximum allowed size for the page: 30");
    }

    long offset = pageable.getOffset();
    int size = pageable.getPageSize();

    var players =
        playerRepository.customFindAll(keyword, offset, size).stream()
            .map(PlayerMinResponseDTO::new)
            .collect(Collectors.toList());

    long totalElements = playerRepository.countCustomFindAll(keyword);

    String[] sortFieldAndDirection = pageable.getSort().toString().split(": ");
    String field = sortFieldAndDirection[0];
    String direction = sortFieldAndDirection[1];

    Comparator<PlayerMinResponseDTO> comparator =
        switch (field) {
          case "team" -> Comparator.comparing(PlayerMinResponseDTO::team);
          case "position" -> Comparator.comparing(player -> player.positionDTO().name());
          default -> Comparator.comparing(PlayerMinResponseDTO::name);
        };

    players.sort(direction.equalsIgnoreCase("ASC") ? comparator : comparator.reversed());
    return new PageImpl<>(players, pageable, totalElements);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public PlayerResponseDTO findById(Long id) {
    try {
      var projections = playerRepository.customFindById(id);
      var oneProjection = projections.get(0);
      var parameters = extractPlayerParameters(projections);

      return new PlayerResponseDTO(oneProjection, parameters);

    } catch (Exception e) {
      throw new EntityNotFoundException("Player ID not found: " + id);
    }
  }

  private List<PlayerParameterDataDTO> extractPlayerParameters(List<PlayerProjection> projections) {
    if (projections.get(0).getParameterId() != null) {
      return projections.stream()
          .map(
              p -> {
                long parameterId = p.getParameterId();
                String parameterName = p.getParameterName();
                int playerScore = p.getPlayerScore();
                return new PlayerParameterDataDTO(parameterId, parameterName, playerScore);
              })
          .toList();
    }

    return new ArrayList<>();
  }

  public PlayerPicture findPictureById(Long id) {
    return playerPictureRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Player picture ID not found: " + id));
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public PlayerResponseDTO save(PlayerRequestDTO playerRequestDTO) {
    try {
      Player newPlayer = new Player(playerRequestDTO);
      PlayerPicture playerPicture = new PlayerPicture(newPlayer, playerRequestDTO.playerPicture());
      Position position =
          positionRepository
              .findById(playerRequestDTO.positionId())
              .orElseThrow(
                  () -> new EntityNotFoundException("Position ID not found: " + playerRequestDTO.positionId()));
      List<PlayerParameterIdScoreDTO> parameters = parseParameters(playerRequestDTO.parameters());

      newPlayer.setPosition(position);
      newPlayer.setPlayerPicture(playerPicture);

      playerRepository.save(newPlayer);
      playerParameterRepository.saveAllPlayerParameters(newPlayer.getId(), parameters, entityManager);

      return findById(newPlayer.getId());

    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException(e.getMessage());
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public PlayerResponseDTO update(Long id, PlayerRequestDTO playerRequestDTO) {
    try {
      Player player = playerRepository.getReferenceById(id);
      Position position =
          positionRepository
              .findById(playerRequestDTO.positionId())
              .orElseThrow(
                  () -> new EntityNotFoundException("Position ID not found: " + playerRequestDTO.positionId()));
      player.updateData(playerRequestDTO, position);

      List<PlayerParameterIdScoreDTO> parameters = parseParameters(playerRequestDTO.parameters());
      playerParameterRepository.deleteByPlayerId(player.getId());
      playerParameterRepository.saveAllPlayerParameters(player.getId(), parameters, entityManager);

      player = playerRepository.save(player);
      return findById(player.getId());

    } catch (jakarta.persistence.EntityNotFoundException e) {
      throw new EntityNotFoundException("Player ID not found: " + id);

    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException(e.getMessage());
    }
  }

  private List<PlayerParameterIdScoreDTO> parseParameters(String parameters) {
    try {
      return parameters.isBlank() ? new ArrayList<>() : objectMapper.readValue(parameters, new TypeReference<>() {});
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid format for player parameters");
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void deleteById(@NonNull Long id) {
    if (!playerRepository.existsById(id)) {
      throw new EntityNotFoundException("Player ID not found: " + id);
    }
    playerRepository.customDeleteById(id);
  }
}

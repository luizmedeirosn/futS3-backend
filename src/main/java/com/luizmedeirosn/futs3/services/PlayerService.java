package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterIdScoreDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PlayerParameterDataDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PlayerMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
@SuppressWarnings({"java:S2583"})
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ParameterRepository parameterRepository;
    private final PlayerParameterRepository playerParameterRepository;
    private final PositionRepository positionRepository;

    @PersistenceContext
    EntityManager entityManager;

    public PlayerService(
            PlayerRepository playerRepository,
            ParameterRepository parameterRepository,
            PlayerParameterRepository playerParameterRepository,
            PositionRepository positionRepository
    ) {
        this.playerRepository = playerRepository;
        this.parameterRepository = parameterRepository;
        this.playerParameterRepository = playerParameterRepository;
        this.positionRepository = positionRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PlayerMinResponseDTO> findAll() {
        return playerRepository.findAllOptimized().stream().map(PlayerMinResponseDTO::new).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PlayerResponseDTO findById(@NonNull Long id) {
        var playerProjections = playerRepository
                .findByIdOptimized(id)
                .orElseThrow(() -> new EntityNotFoundException("Player ID not found"));

        var playerProjection = playerProjections.get(0);
        var parameters = extractPlayerParameters(playerProjections);

        return new PlayerResponseDTO(playerProjection, parameters);
    }

    private List<PlayerParameterDataDTO> extractPlayerParameters(List<PlayerProjection> playerProjections) {
        if (playerProjections.get(0).getParameterId() != null) {
            return playerProjections
                    .stream()
                    .map(p -> {
                        long parameterId = p.getParameterId();
                        String parameterName = p.getParameterName();
                        int playerScore = p.getPlayerScore();
                        return new PlayerParameterDataDTO(parameterId, parameterName, playerScore);
                    })
                    .toList();
        }

        return new ArrayList<>();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PlayerResponseDTO save(PlayerRequestDTO playerRequestDTO) {
        try {
            Player newPlayer = new Player(playerRequestDTO);
            PlayerPicture playerPicture = new PlayerPicture(newPlayer, playerRequestDTO.playerPicture());
            Position position = positionRepository
                    .findById(playerRequestDTO.positionId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Position ID not found: " + playerRequestDTO.positionId())
                    );

            newPlayer.setPosition(position);
            newPlayer.setPlayerPicture(playerPicture);

            playerRepository.save(newPlayer);
            playerParameterRepository.saveAllOptimized(
                    entityManager,
                    newPlayer.getId(),
                    playerRequestDTO.parameters()
            );

            return findById(newPlayer.getId());

        } catch (NullPointerException | InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Player request. The given IDs must not be null");

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Player request. IDs not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Player request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PlayerMinResponseDTO update(@NonNull Long id, PlayerRequestDTO playerRequestDTO) {
        try {
            Player player = playerRepository.getReferenceById(id);

            player.updateData(playerRequestDTO);

            Long positionId = playerRequestDTO.positionId();
            if (positionId != null) {
                player.setPosition(positionRepository.findById(positionId).get());
            } else {
                throw new NullPointerException();
            }

            playerParameterRepository.deleteByIdPlayerId(player.getId());
            playerParameterRepository.saveAllOptimized(
                    entityManager,
                    player.getId(),
                    playerRequestDTO.parameters()
            );

            player = playerRepository.save(player);
            return new PlayerMinResponseDTO(player);

        } catch (NullPointerException | InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Player request. The given ID must not be null");

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Player request. ID not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Player request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(@NonNull Long id) {
        if (!playerRepository.existsById(id)) {
            throw new EntityNotFoundException("Player request. ID not found");
        }
        playerRepository.deleteByIdWithParameters(id);
    }

}
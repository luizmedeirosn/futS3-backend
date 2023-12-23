package com.luizmedeirosn.futs3.services;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.projections.player.AllPlayersParametersProjection;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterScoreDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerWithProjectionResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PlayerMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final ParameterRepository parameterRepository;

    private final PlayerParameterRepository playerParameterRepository;

    private final PositionRepository positionRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PlayerMinResponseDTO> findAll() {
        return playerRepository.findAllOptimized().stream().map(PlayerMinResponseDTO::new).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PlayerMinResponseDTO findById(Long id) {
        try {
            return new PlayerMinResponseDTO(playerRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Player ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PlayerWithProjectionResponseDTO findFullById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player ID not found"));
        return new PlayerWithProjectionResponseDTO(player, parameterRepository.findParametersByPlayerId(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllPlayersParametersProjection> findAllWithParameters() {
        return playerParameterRepository.findAllPlayersParameters();
    }

    @SuppressWarnings("java:S112")
    private void savePlayerParameters(Player player, String parametersSTR) throws RuntimeException {
        if (parametersSTR.length() > 3) {
            List<String> parametersSTRList = Arrays.asList(parametersSTR.split(","));
            List<PlayerParameterScoreDTO> parameters = parametersSTRList.stream()
                    .map(x -> new PlayerParameterScoreDTO(Long.parseLong(x.split(" ")[0]),
                            Integer.parseInt(x.split(" ")[1])))
                    .toList();

            parameters.forEach(
                    parameterScore -> {
                        Parameter parameter = parameterRepository.findById(parameterScore.id()).get();
                        PlayerParameter playerParameter = new PlayerParameter(player, parameter,
                                parameterScore.score());

                        playerParameterRepository.save(playerParameter);
                    });
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PlayerResponseDTO save(PlayerRequestDTO playerRequestDTO) {
        try {
            Player newPlayer = new Player(playerRequestDTO);
            newPlayer.setPosition(positionRepository.findById(playerRequestDTO.positionId()).get());

            if (playerRequestDTO.playerPicture() != null) {
                PlayerPicture playerPicture = new PlayerPicture(newPlayer, playerRequestDTO.playerPicture());
                newPlayer.setPlayerPicture(playerPicture);
            }

            playerRepository.save(newPlayer);
            savePlayerParameters(newPlayer, playerRequestDTO.parameters());

            return new PlayerResponseDTO(newPlayer);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Player request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Player request. The given IDs must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Player request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PlayerResponseDTO update(Long id, PlayerRequestDTO playerRequestDTO) {
        try {
            Player player = playerRepository.findById(id).get();

            player.updateData(playerRequestDTO);
            player.setPosition(positionRepository.findById(playerRequestDTO.positionId()).get());

            player.getPlayerParameters().clear();
            playerParameterRepository.deleteAllById(
                    player.getPlayerParameters().stream().map(p -> p.getId().getPlayer().getId()).toList());

            savePlayerParameters(player, playerRequestDTO.parameters());
            player = playerRepository.save(player);

            return new PlayerResponseDTO(player);

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Player request. ID not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new EntityNotFoundException("Player request. ID not found");
        }
        Player player = playerRepository.getReferenceById(id);
        playerRepository.deleteByIdWithParameters(player.getId());
    }

}
package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterScoreDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PlayerMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@SuppressWarnings({"java:S2583"})
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ParameterRepository parameterRepository;
    private final PlayerParameterRepository playerParameterRepository;
    private final PositionRepository positionRepository;

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
        return new PlayerResponseDTO(
                playerRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Player ID not found"))
        );
    }

    private void savePlayerParameters(Player player, String parametersSTR) {
        if (parametersSTR.length() > 3) {
            List<String> parametersSTRList = Arrays.asList(parametersSTR.split(","));
            List<PlayerParameterScoreDTO> parameters = parametersSTRList.stream()
                    .map(x -> new PlayerParameterScoreDTO(Long.parseLong(x.split(" ")[0]),
                            Integer.parseInt(x.split(" ")[1])))
                    .toList();

            parameters.forEach(
                    parameterScore -> {
                        Long parameterScoreId = parameterScore.id();
                        if (parameterScoreId != null) {
                            Parameter parameter = parameterRepository.findById(parameterScoreId).get();
                            PlayerParameter playerParameter = new PlayerParameter(player, parameter,
                                    parameterScore.score());
                            playerParameterRepository.save(playerParameter);

                        } else {
                            throw new EntityNotFoundException("Player request. The given ID must not be null");
                        }
                    });
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PlayerResponseDTO save(PlayerRequestDTO playerRequestDTO) {
        try {
            Player newPlayer = new Player(playerRequestDTO);
            PlayerPicture playerPicture = new PlayerPicture(newPlayer, playerRequestDTO.playerPicture());

            newPlayer.setPosition(positionRepository
                    .findById(playerRequestDTO.positionId())
                    .orElseThrow(() -> new EntityNotFoundException("Position ID not found: " + playerRequestDTO.positionId()))
            );
            newPlayer.setPlayerPicture(playerPicture);

            playerRepository.save(newPlayer);
            savePlayerParameters(newPlayer, playerRequestDTO.parameters());

            return new PlayerResponseDTO(newPlayer, parameterRepository.findParametersByPlayerId(newPlayer.getId()));

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
            savePlayerParameters(player, playerRequestDTO.parameters());

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
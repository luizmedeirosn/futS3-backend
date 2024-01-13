package com.luizmedeirosn.futs3.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.projections.gamemode.AllGameModesProjection;
import com.luizmedeirosn.futs3.projections.gamemode.GameModePositionProjection;
import com.luizmedeirosn.futs3.projections.gamemode.GameModePositionsParametersProjection;
import com.luizmedeirosn.futs3.repositories.GameModeRepository;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.GameModeRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.GameModeResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerFullScoreResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.aux.GameModePositionParameterDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.GameModeMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameModeService {

    private final GameModeRepository gameModeRepository;

    private final PositionRepository positionRepository;

    private final ParameterRepository parameterRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<GameModeMinResponseDTO> findAll() {
        return gameModeRepository
                .findAll(Sort.by("formationName"))
                .stream()
                .map(GameModeMinResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public GameModeMinResponseDTO findById(@NonNull Long id) {
        try {
            return new GameModeMinResponseDTO(gameModeRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("GameMode ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllGameModesProjection> findAllFull() {
        return gameModeRepository.findAllFull();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public GameModeResponseDTO findFullById(@NonNull Long id) {
        try {

            List<GameModePositionProjection> positions = gameModeRepository.findGameModePositions(id).get();
            List<GameModePositionsParametersProjection> positionsParameters = gameModeRepository
                    .findByIdWithPositionsParameters(id);

            List<GameModePositionParameterDTO> fields = new ArrayList<>();

            for (GameModePositionsParametersProjection positionParameter : positionsParameters) {
                fields.add(new GameModePositionParameterDTO(
                        positionParameter.getPositionId(),
                        positionParameter.getPositionName(),
                        positionParameter.getParameterId(),
                        positionParameter.getParameterName(),
                        positionParameter.getParameterWeight()));

                positions.removeIf(
                        position -> Objects.equals(position.getPositionId(), positionParameter.getPositionId()));
            }

            positions.forEach(position -> fields.add(new GameModePositionParameterDTO(position.getPositionId(),
                    position.getPositionName(), null, null, null)));

            return new GameModeResponseDTO(gameModeRepository.findById(id).get(), fields);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("GameMode ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<GameModePositionProjection> findGameModePositions(@NonNull Long id) {
        if (!gameModeRepository.existsById(id)) {
            throw new EntityNotFoundException("GameMode request. ID not found");
        }

        return gameModeRepository.findGameModePositions(id)
                .orElseThrow(() -> new DatabaseException("GameMode ID not found"));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PlayerFullScoreResponseDTO> getPlayersRanking(@NonNull Long gameModeId, @NonNull Long positionId) {
        return gameModeRepository.getPlayersRanking(gameModeId, positionId)
                .orElseThrow(() -> new DatabaseException("Error getting the ranking"))
                .stream()
                .map(player -> new PlayerFullScoreResponseDTO(player,
                        parameterRepository.findParametersByPlayerId(player.getPlayerId())))
                .toList();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public GameModeResponseDTO save(GameModeRequestDTO gameMode) {
        try {
            GameMode newGameMode = new GameMode();
            newGameMode.setFormationName(gameMode.formationName());
            newGameMode.setDescription(gameMode.description());

            Set<Position> positions = newGameMode.getPositions();
            gameMode.positions().forEach(positionId -> {
                if (positionId != null) {
                    positions.add(positionRepository.findById(positionId).get());
                } else {
                    throw new NullPointerException();
                }
            });

            gameModeRepository.save(newGameMode);

            Long id = newGameMode.getId();
            if (id != null) {
                return findFullById(id);
            } else {
                throw new NullPointerException();
            }

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("GameMode request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("GameMode request. The given IDs must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("GameMode request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public GameModeMinResponseDTO update(@NonNull Long id, GameModeRequestDTO gameModeRequestDTO) {
        try {
            GameMode gameMode = gameModeRepository.getReferenceById(id);
            gameMode.updateData(gameModeRequestDTO);

            gameModeRepository.deleteByIdFromTbGameModePosition(id);
            Set<Position> positions = gameMode.getPositions();

            gameModeRequestDTO.positions()
                    .forEach(positionId -> {
                        if (positionId != null) {
                            positions.add(positionRepository.findById(positionId).get());
                        } else {
                            throw new NullPointerException();
                        }
                    });

            gameMode = gameModeRepository.save(gameMode);
            return new GameModeMinResponseDTO(gameMode);

        } catch (NullPointerException e) {
            throw new EntityNotFoundException("GameMode request. The given IDs must not be null");

        } catch (jakarta.persistence.EntityNotFoundException e) {
            // jakarta.persistence.EntityNotFoundException: Unable to find
            // com.luizmedeirosn.futs3.entities.GameMode with id 10
            throw new EntityNotFoundException("GameMode request. ID not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("GameMode request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(@NonNull Long id) {
        if (!gameModeRepository.existsById(id)) {
            throw new EntityNotFoundException("GameMode request. ID not found");
        }
        gameModeRepository.deleteById(id);
    }

}
package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
import com.luizmedeirosn.futs3.repositories.GameModeRepository;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.GameModeRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.GameModeResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerFullDataResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PositionResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PositionParametersDataDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.GameModeMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameModeService {

    private final GameModeRepository gameModeRepository;
    private final PositionRepository positionRepository;
    private final ParameterRepository parameterRepository;

    public GameModeService(
            GameModeRepository gameModeRepository,
            PositionRepository positionRepository,
            ParameterRepository parameterRepository
    ) {
        this.gameModeRepository = gameModeRepository;
        this.positionRepository = positionRepository;
        this.parameterRepository = parameterRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<GameModeMinResponseDTO> findAll() {
        return gameModeRepository
                .findAll(Sort.by("formationName"))
                .stream()
                .map(GameModeMinResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public GameModeResponseDTO findById(Long id) {
        var gameModeMin = gameModeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GameMode ID not found:" + id));


        var projections = gameModeRepository.findPositionsDataByGameModeId(id);
        var positions = extractPositionsData(projections);

        return new GameModeResponseDTO(
                gameModeMin.getId(),
                gameModeMin.getFormationName(),
                gameModeMin.getDescription(),
                positions
        );
    }

    private List<PositionResponseDTO> extractPositionsData(List<PositionParametersProjection> projections) {
        Map<Position, List<PositionParametersDataDTO>> positionsParameters = new HashMap<>();

        projections.forEach(p -> positionsParameters
                .computeIfAbsent(
                        new Position(p.getId(), p.getName(), p.getDescription()),
                        k -> new ArrayList<>()
                )
                .add(new PositionParametersDataDTO(p.getParameterId(), p.getParameterName(), p.getPositionWeight()))
        );

        return mapToPositionResponseDTO(positionsParameters);
    }

    private List<PositionResponseDTO> mapToPositionResponseDTO(Map<Position, List<PositionParametersDataDTO>> positionsParameters) {
        return positionsParameters
                .entrySet()
                .stream()
                .map(
                        entry -> new PositionResponseDTO(
                                entry.getKey().getId(),
                                entry.getKey().getName(),
                                entry.getKey().getDescription(),
                                entry.getValue()
                        ))
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PlayerFullDataResponseDTO> getPlayersRanking(
            Long gameModeId,
            Long positionId
    ) {
        try {
            return gameModeRepository.getPlayersRanking(gameModeId, positionId)
                    .stream()
                    .map(player -> new PlayerFullDataResponseDTO(player,
                            parameterRepository.findParametersByPlayerId(player.getPlayerId())))
                    .toList();

        } catch (Exception e) {
            throw new DatabaseException("Error getting the ranking");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
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
                return findById(id);
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public GameModeMinResponseDTO update(Long id, GameModeRequestDTO gameModeRequestDTO) {
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        if (!gameModeRepository.existsById(id)) {
            throw new EntityNotFoundException("GameMode request. ID not found");
        }
        gameModeRepository.deleteById(id);
    }

}
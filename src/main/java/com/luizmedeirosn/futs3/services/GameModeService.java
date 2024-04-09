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
import jakarta.persistence.EntityManager;
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
    private final EntityManager entityManager;

    public GameModeService(
            GameModeRepository gameModeRepository,
            PositionRepository positionRepository,
            ParameterRepository parameterRepository,
            EntityManager entityManager
    ) {
        this.gameModeRepository = gameModeRepository;
        this.positionRepository = positionRepository;
        this.parameterRepository = parameterRepository;
        this.entityManager = entityManager;
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
        Map<Position, List<PositionParametersDataDTO>> positionsParameters = new LinkedHashMap<>();

        projections.forEach(p -> {
            Position position = new Position(p.getId(), p.getName(), p.getDescription());
            positionsParameters.computeIfAbsent(position, k -> new ArrayList<>());
            if (p.getParameterId() != null) {
                positionsParameters
                        .get(position)
                        .add(new PositionParametersDataDTO(p.getParameterId(), p.getParameterName(), p.getPositionWeight()));
            }
        });

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
    public GameModeResponseDTO save(GameModeRequestDTO gameModeRequestDTO) {
        try {
            GameMode newGameMode = new GameMode(
                    gameModeRequestDTO.formationName(),
                    gameModeRequestDTO.description()
            );

            newGameMode = gameModeRepository.save(newGameMode);
            gameModeRepository.saveAllPositions(
                    newGameMode.getId(),
                    gameModeRequestDTO.positions(),
                    entityManager
            );

            return findById(newGameMode.getId());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("GameMode request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("GameMode request. The given IDs must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("GameMode request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public GameModeResponseDTO update(Long id, GameModeRequestDTO gameModeRequestDTO) {
        try {
            GameMode gameMode = gameModeRepository.getReferenceById(id);
            gameMode.updateData(gameModeRequestDTO);

            gameModeRepository.deletePositionsFromGameModeById(gameMode.getId());
            gameModeRepository.saveAllPositions(
                    gameMode.getId(),
                    gameModeRequestDTO.positions(),
                    entityManager
            );

            gameMode = gameModeRepository.save(gameMode);
            return findById(gameMode.getId());

        } catch (NullPointerException e) {
            throw new EntityNotFoundException("GameMode request. The given IDs must not be null");

        } catch (jakarta.persistence.EntityNotFoundException e) {
            // jakarta.persistence.EntityNotFoundException: Unable to find
            // com.luizmedeirosn.futs3.entities.GameMode with id 10
            throw new EntityNotFoundException("GameMode request. ID not found: " + id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("GameMode request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        if (!gameModeRepository.existsById(id)) {
            throw new EntityNotFoundException("GameMode ID not found: " + id);
        }
        gameModeRepository.deleteById(id);
    }
}
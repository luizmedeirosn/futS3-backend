package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerDataScoreProjection;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
import com.luizmedeirosn.futs3.repositories.GameModeRepository;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.shared.dto.request.GameModeRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.GameModeResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerFullDataResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PositionResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PlayerParameterDataDTO;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PositionParametersDataDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.GameModeMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import com.luizmedeirosn.futs3.shared.exceptions.PageableException;
import jakarta.persistence.EntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameModeService {

    private final GameModeRepository gameModeRepository;
    private final PlayerParameterRepository playerParameterRepository;
    private final EntityManager entityManager;

    public GameModeService(
            GameModeRepository gameModeRepository,
            PlayerParameterRepository playerParameterRepository,
            EntityManager entityManager
    ) {
        this.gameModeRepository = gameModeRepository;
        this.playerParameterRepository = playerParameterRepository;
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
    public List<PlayerFullDataResponseDTO> getPlayersRanking(Long gameModeId, Long positionId, Pageable pageable) {
        try {
            if (pageable.getPageSize() > 30) {
                throw new PageableException("The maximum allowed size for the page: 30");
            }

            var playersRanking =
                    gameModeRepository.getPlayersRanking(gameModeId, positionId, pageable.getOffset(), pageable.getPageSize());
            var playersIds =
                    playersRanking.stream().map(PlayerDataScoreProjection::getPlayerId).toList();

            var filteredParameters =
                    playerParameterRepository.findParametersByPlayerIds(playersIds);

            return playersRanking
                    .stream()
                    .map(playerProjection -> {
                        var parameters = new ArrayList<PlayerParameterDataDTO>();

                        var iterator = filteredParameters.iterator();
                        while (iterator.hasNext()) {
                            var parameter = iterator.next();
                            if (Objects.equals(parameter.getPlayerId(), playerProjection.getPlayerId())) {
                                parameters.add(new PlayerParameterDataDTO(parameter));
                                iterator.remove();
                            }
                        }

                        return new PlayerFullDataResponseDTO(playerProjection, parameters);
                    })
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

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
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

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("GameMode ID not found: " + id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        if (!gameModeRepository.existsById(id)) {
            throw new EntityNotFoundException("GameMode ID not found: " + id);
        }
        gameModeRepository.customDeleteById(id);
    }
}
package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.projections.gamemode.AllGameModesProjection;
import com.luizmedeirosn.futs3.projections.gamemode.GameModePositionProjection;
import com.luizmedeirosn.futs3.repositories.GameModeRepository;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.GameModeRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.GameModeResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerFullScoreResponseDTO;
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

    private final PositionParameterRepository positionParameterRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<GameModeMinResponseDTO> findAll() {
        return gameModeRepository
                .findAll(Sort.by("id"))
                .stream()
                .map(GameModeMinResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public GameModeMinResponseDTO findById(Long id) {
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
    public GameModeResponseDTO findFullById(Long id) {
        try {
            return new GameModeResponseDTO(gameModeRepository.findById(id).get(), gameModeRepository.findFullById(id));

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("GameMode ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<GameModePositionProjection> findGameModePositions(Long id) {
        return gameModeRepository.findGameModePositions(id)
                .orElseThrow(() -> new DatabaseException("GameMode ID not found"));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PlayerFullScoreResponseDTO> getPlayersRanking(Long gameModeId, Long positionId) {
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
            newGameMode.setFormationName(gameMode.getFormationName());
            newGameMode.setDescription(gameMode.getDescription());

            Set<Position> positions = newGameMode.getPositions();
            gameMode
                    .getPositionsParameters()
                    .forEach(
                            positionParameters -> {
                                Position pos = positionRepository.findById(positionParameters.getPositionId()).get();
                                positions.add(pos);
                                positionParameters
                                        .getParameters()
                                        .forEach(
                                                parameterWeight -> positionParameterRepository.save(
                                                        new PositionParameter(pos, parameterRepository
                                                                .findById(parameterWeight.getParameterId()).get(),
                                                                parameterWeight.getWeight())));
                            });
            gameModeRepository.save(newGameMode);
            return findFullById(newGameMode.getId());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("GameMode request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("GameMode request. The given IDs must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("GameMode request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public GameModeMinResponseDTO update(Long id, GameModeRequestDTO gameModeRequestDTO) {
        try {
            GameMode gameModeResponseDTO = gameModeRepository.getReferenceById(id);
            gameModeResponseDTO.updateData(gameModeRequestDTO);
            gameModeResponseDTO = gameModeRepository.save(gameModeResponseDTO);
            return new GameModeMinResponseDTO(gameModeResponseDTO);

        } catch (jakarta.persistence.EntityNotFoundException e) {
            // jakarta.persistence.EntityNotFoundException: Unable to find
            // com.luizmedeirosn.futs3.entities.GameMode with id 10
            throw new EntityNotFoundException("GameMode request. ID not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("GameMode request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        if (!gameModeRepository.existsById(id)) {
            throw new EntityNotFoundException("GameMode request. ID not found");
        }
        gameModeRepository.deleteById(id);
    }

}
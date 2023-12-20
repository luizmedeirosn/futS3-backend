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
import com.luizmedeirosn.futs3.shared.dto.request.post.PostGameModeDTO;
import com.luizmedeirosn.futs3.shared.dto.request.update.UpdateGameModeDTO;
import com.luizmedeirosn.futs3.shared.dto.response.GameModeDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerFullScoreDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.GameModeMinDTO;
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
    public List<GameModeMinDTO> findAll() {
        return gameModeRepository
                .findAll(Sort.by("id"))
                .stream()
                .map(GameModeMinDTO::new)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public GameModeMinDTO findById(Long id) {
        try {
            return new GameModeMinDTO(gameModeRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("GameMode ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllGameModesProjection> findAllFull() {
        return gameModeRepository.findAllFull();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public GameModeDTO findFullById(Long id) {
        try {
            return new GameModeDTO(gameModeRepository.findById(id).get(), gameModeRepository.findFullById(id));

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
    public List<PlayerFullScoreDTO> getPlayersRanking(Long gameModeId, Long positionId) {
        return gameModeRepository.getPlayersRanking(gameModeId, positionId)
                .orElseThrow(() -> new DatabaseException("Error getting the ranking"))
                .stream()
                .map(player -> new PlayerFullScoreDTO(player,
                        parameterRepository.findParametsByPlayerId(player.getPlayerId())))
                .toList();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public GameModeDTO save(PostGameModeDTO postGameModeDTO) {
        try {
            GameMode newGameMode = new GameMode();
            newGameMode.setFormationName(postGameModeDTO.getFormationName());
            newGameMode.setDescription(postGameModeDTO.getDescription());

            Set<Position> positions = newGameMode.getPositions();
            postGameModeDTO
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
    public GameModeMinDTO update(Long id, UpdateGameModeDTO updateGameModeDTO) {
        try {
            GameMode gameMode = gameModeRepository.getReferenceById(id);
            gameMode.updateData(updateGameModeDTO);
            gameMode = gameModeRepository.save(gameMode);
            return new GameModeMinDTO(gameMode);

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
package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizmedeirosn.futs3.dto.request.post.PostPlayerDTO;
import com.luizmedeirosn.futs3.dto.request.update.UpdatePlayerDTO;
import com.luizmedeirosn.futs3.dto.response.PlayerDTO;
import com.luizmedeirosn.futs3.dto.response.min.PlayerMinDTO;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.projections.player.AllPlayersParametersProjection;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private PlayerParameterRepository playerParameterRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PlayerMinDTO> findAll() {
        return playerRepository.findAllOptimized().stream().map(PlayerMinDTO::new).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PlayerMinDTO findById(Long id) {
        try {
            return new PlayerMinDTO(playerRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Player ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PlayerDTO findFullById(Long id) {
        try {
            return new PlayerDTO(playerRepository.findById(id).get(), parameterRepository.findByPlayerId(id));

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Player ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllPlayersParametersProjection> findAllWithParameters() {
        return playerParameterRepository.findAllPlayersParameters();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PlayerDTO save(PostPlayerDTO playerInputDTO) {
        try {
            Player newPlayer = new Player();
            newPlayer.setName(playerInputDTO.getName());
            newPlayer.setPosition(positionRepository.findById(playerInputDTO.getPositionId()).get());
            playerRepository.save(newPlayer);

            playerInputDTO.getParameters().forEach(
                    parameterScore -> {
                        Parameter parameter = parameterRepository.findById(parameterScore.getId()).get();
                        PlayerParameter playerParameter = new PlayerParameter(newPlayer, parameter,
                                parameterScore.getPlayerScore());
                        playerParameterRepository.save(playerParameter);
                    });
            return new PlayerDTO(newPlayer, parameterRepository.findByPlayerId(newPlayer.getId()));

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Player request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Player request. The given IDs must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Player request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PlayerMinDTO update(Long id, UpdatePlayerDTO updatePlayerDTO) {
        try {
            Player player = playerRepository.getReferenceById(id);
            player.updateData(updatePlayerDTO.getName(),
                    positionRepository.findById(updatePlayerDTO.getPositionId()).get());
            player = playerRepository.save(player);
            return new PlayerMinDTO(player);

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Player request. ID not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Player request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new EntityNotFoundException("Player request. ID not found");
        }
        playerRepository.deleteById(id);
    }

}
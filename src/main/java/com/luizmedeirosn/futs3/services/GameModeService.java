package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.input.post.PostGameModeDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdateGameModeDTO;
import com.luizmedeirosn.futs3.dto.output.GameModeDTO;
import com.luizmedeirosn.futs3.dto.output.min.GameModeMinDTO;
import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.projections.AllGameModesProjection;
import com.luizmedeirosn.futs3.repositories.GameModeRepository;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;

@Service
public class GameModeService {

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    PositionParameterRepository positionParameterRepository;

    public List<GameModeMinDTO> findAll() {
        return gameModeRepository
            .findAll(Sort.by("id"))
            .stream()
            .map( x -> new GameModeMinDTO(x) )
            .toList();
    }

    public GameModeMinDTO findById(Long id) {
        return new GameModeMinDTO(gameModeRepository.findById(id).get());
    }

    public List<AllGameModesProjection> findAllFull() {
        List<AllGameModesProjection> fullGameModes = gameModeRepository.findAllFull();
        return fullGameModes;
    }

    public GameModeDTO findFullById(Long id) {
        return new GameModeDTO( gameModeRepository.findById(id).get(), gameModeRepository.findFullById(id) );
    }

    public GameModeDTO save(PostGameModeDTO postGameModeDTO) {
        GameMode newGameMode = new GameMode();
        newGameMode.setFormationName(postGameModeDTO.getFormationName());
        newGameMode.setDescription(postGameModeDTO.getDescription());
        
        Set<Position> positions = newGameMode.getPositions();
        postGameModeDTO
            .getPositionsParameters()
            .forEach (
                positionParameters -> {
                    Position pos = positionRepository.findById( positionParameters.getPositionId() ).get();
                    positions.add(pos);
                    positionParameters
                        .getParameters()
                        .forEach (
                            parameterWeight -> positionParameterRepository.save ( 
                                new PositionParameter( pos, parameterRepository.findById(parameterWeight.getParameterId()).get(), parameterWeight.getWeight() )
                            )
                        );
                }
            );
        gameModeRepository.save(newGameMode);
        GameModeDTO gameModeDTO = findFullById(newGameMode.getId());
        return gameModeDTO;
    }

    public GameModeMinDTO update(Long id, UpdateGameModeDTO updateGameModeDTO) {
        GameMode gameMode = gameModeRepository.getReferenceById(id);
        gameMode.updateData(updateGameModeDTO);
        gameMode = gameModeRepository.save(gameMode);
        return new GameModeMinDTO(gameMode);
    }

    public void deleteById(Long id) {
        gameModeRepository.deleteById(id);
    }
    
}
package com.luizmedeirosn.futs3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.input.post.PostPlayerDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdatePlayerDTO;
import com.luizmedeirosn.futs3.dto.output.PlayerDTO;
import com.luizmedeirosn.futs3.dto.output.min.PlayerMinDTO;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.projections.AllPlayersParametersProjection;
import com.luizmedeirosn.futs3.projections.PlayerProjection;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;

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

    public List<PlayerProjection> findAll() {
        return playerRepository.findAllOptimized();
    }

    public PlayerMinDTO findById(Long id) {
        return new PlayerMinDTO( playerRepository.findById(id).get());
    }


    public PlayerDTO findByIdWithParameters(Long id) {
        return new PlayerDTO( playerRepository.findById(id).get(), parameterRepository.findByPlayerId(id) );
    }

    public List<AllPlayersParametersProjection> findAllWithParameters() {
        return playerParameterRepository.findAllPlayersParameters();
    }

    public PlayerDTO save(PostPlayerDTO playerInputDTO) {
        Player newPlayer = new Player();
        newPlayer.setName(playerInputDTO.getName());
        newPlayer.setPosition( positionRepository.findById(playerInputDTO.getPositionId()).get() );
        playerRepository.save(newPlayer);

        playerInputDTO.getParameters().forEach (
            parameterScore -> {
                Parameter parameter = parameterRepository.findById(parameterScore.getId()).get();
                PlayerParameter playerParameter = 
                    new PlayerParameter( newPlayer, parameter, parameterScore.getPlayerScore() );
                playerParameterRepository.save(playerParameter);
            }
        );
        return new PlayerDTO( newPlayer, parameterRepository.findByPlayerId(newPlayer.getId()) );
    }

    public PlayerMinDTO update(Long id, UpdatePlayerDTO updatePlayerDTO) {
        Player player = playerRepository.getReferenceById(id);
        player.updateData( updatePlayerDTO.getName(), positionRepository.findById(updatePlayerDTO.getPositionId()).get() );
        player = playerRepository.save(player);
        return new PlayerMinDTO(player);
    }

    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

}
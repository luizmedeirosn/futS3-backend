package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.input.post.PostPlayerDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdatePlayerDTO;
import com.luizmedeirosn.futs3.dto.output.PlayerDTO;
import com.luizmedeirosn.futs3.dto.output.min.PlayerMinDTO;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerParameter;
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

    public PlayerDTO findByIdWithParameters(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        PlayerDTO playerDTO = new PlayerDTO( playerOptional.get(), parameterRepository.findByPlayerId(id) );
        return playerDTO;
    }

    public Set<PlayerDTO> findAllWithParameters() {
        List<Player> playerList = playerRepository.findAll();
        Set<PlayerDTO> playersDTO = new TreeSet<>();
        playerList.forEach( obj -> playersDTO.add(findByIdWithParameters(obj.getId())) );
        return playersDTO;
    }

    public Set<PlayerMinDTO> findAll() {
        Set<PlayerMinDTO> playerMinDTOs = new TreeSet<>();
        playerRepository.findAll().forEach( obj -> playerMinDTOs.add( new PlayerMinDTO(obj)) );
        return playerMinDTOs;
    }

    public PlayerMinDTO findById(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        PlayerMinDTO playerMinDTO = new PlayerMinDTO(playerOptional.get());
        return playerMinDTO;
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
        PlayerDTO playerDTO = new PlayerDTO( newPlayer, parameterRepository.findByPlayerId(newPlayer.getId()) );
        return playerDTO;
    }

    public PlayerMinDTO update(Long id, UpdatePlayerDTO updatePlayerDTO) {
        Player player = playerRepository.getReferenceById(id);
        player.updateData( updatePlayerDTO.getName(), positionRepository.findById(updatePlayerDTO.getPositionId()).get() );
        player = playerRepository.save(player);
        PlayerMinDTO playerMinDTO = new PlayerMinDTO(player);
        return playerMinDTO;
    }

    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

}
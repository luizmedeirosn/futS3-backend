package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.PlayerDTO;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.repositories.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ParameterSerivce parameterSerivce;

    public PlayerDTO findByIdWithParameters(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        PlayerDTO playerDTO = new PlayerDTO( playerOptional.get(), parameterSerivce.findByPlayerId(id) );
        return playerDTO;
    }

    public Set<PlayerDTO> findAllWithParameters() {
        List<Player> playerList = playerRepository.findAll();
        Set<PlayerDTO> playersDTO = new TreeSet<>();
        playerList.forEach( obj -> playersDTO.add(findByIdWithParameters(obj.getId())) );
        return playersDTO;
    }

    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }
}
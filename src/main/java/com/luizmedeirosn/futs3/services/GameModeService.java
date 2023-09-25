package com.luizmedeirosn.futs3.services;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.input.update.UpdateGameModeDTO;
import com.luizmedeirosn.futs3.dto.output.min.GameModeMinDTO;
import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.repositories.GameModeRepository;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;

@Service
public class GameModeService {

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    PositionParameterRepository positionParameterRepository;

    public Set<GameModeMinDTO> findAll() {
        Set<GameModeMinDTO> gameModeMinDTOs = new TreeSet<>();
        gameModeRepository.findAll().forEach( obj -> gameModeMinDTOs.add(new GameModeMinDTO(obj)) );
        return gameModeMinDTOs;
    }

    public GameModeMinDTO findById(Long id) {
        Optional<GameMode> gameModeOptional = gameModeRepository.findById(id);
        GameModeMinDTO gameModeMinDTO = new GameModeMinDTO(gameModeOptional.get());
        return gameModeMinDTO;
    }

    public GameMode save(GameMode entity) {
        Set<Position> set = entity.getPositions();
        for (Position position : set) {
            for (PositionParameter posparam : position.getPositionParameters()) {
                posparam.setPosition(position);
                positionParameterRepository.save(posparam);
            }
        }
        entity = gameModeRepository.save(entity);
        return entity;
    }

    public GameMode update(Long id, UpdateGameModeDTO obj) {
        GameMode entity = gameModeRepository.getReferenceById(id);
        entity.updateData(obj);
        entity = gameModeRepository.save(entity);
        return entity;
    }

    public void deleteById(Long id) {
        gameModeRepository.deleteById(id);
    }
}
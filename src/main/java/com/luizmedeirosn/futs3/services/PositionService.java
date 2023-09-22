package com.luizmedeirosn.futs3.services;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.input.post.PostPositionDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdatePositionDTO;
import com.luizmedeirosn.futs3.dto.output.PositionDTO;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.repositories.PositionRepository;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public Set<PositionDTO> findAll() {
        Set<PositionDTO> positionDTOs = new TreeSet<>();
        positionRepository.findAll().forEach( obj -> positionDTOs.add( new PositionDTO(obj)) );
        return positionDTOs;
    }

    public PositionDTO findById(Long id) {
        Optional<Position> optionalPosition = positionRepository.findById(id);
        PositionDTO positionDTO = new PositionDTO(optionalPosition.get());
        return positionDTO;
    }

    public PositionDTO save(PostPositionDTO postPositionDTO) {
        Position position = positionRepository.save(new Position(postPositionDTO.getName(), postPositionDTO.getDescription()));
        PositionDTO positionDTO = new PositionDTO(position);
        return positionDTO;
    }

    public PositionDTO update(Long id, UpdatePositionDTO updatePositionDTO) {
        Position position = positionRepository.getReferenceById(id);
        position.updateData(updatePositionDTO);
        position = positionRepository.save(position);
        PositionDTO positionDTO = new PositionDTO(position);
        return positionDTO;
    }

    public void deleteById(Long id) {
        positionRepository.deleteById(id);
    }
}
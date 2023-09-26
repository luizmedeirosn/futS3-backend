package com.luizmedeirosn.futs3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public List<PositionDTO> findAll() {
        return positionRepository
            .findAll(Sort.by("id"))
            .stream()
            .map( x -> new PositionDTO(x) )
            .toList();
    }

    public PositionDTO findById(Long id) {
        return new PositionDTO(positionRepository.findById(id).get());
    }

    public PositionDTO save(PostPositionDTO postPositionDTO) {
        Position position = positionRepository.save(new Position(postPositionDTO.getName(), postPositionDTO.getDescription()));
        return new PositionDTO(position);
    }

    public PositionDTO update(Long id, UpdatePositionDTO updatePositionDTO) {
        Position position = positionRepository.getReferenceById(id);
        position.updateData(updatePositionDTO);
        position = positionRepository.save(position);
        return new PositionDTO(position);
    }

    public void deleteById(Long id) {
        positionRepository.deleteById(id);
    }
    
}
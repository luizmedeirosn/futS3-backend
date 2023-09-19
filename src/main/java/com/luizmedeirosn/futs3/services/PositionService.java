package com.luizmedeirosn.futs3.services;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.PositionDTO;
import com.luizmedeirosn.futs3.dto.min.PositionMinDTO;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.repositories.PositionRepository;

@Service
public class PositionService {

    @Autowired
    private PositionRepository repository;

    public Set<PositionDTO> findAll() {
        Set<PositionDTO> set = new TreeSet<>();
        repository.findAll().forEach( x -> set.add( new PositionDTO(x)) );
        return set;
    }

    public PositionDTO findById(Long id) {
        Optional<Position> entity = repository.findById(id);
        PositionDTO positionDTO = new PositionDTO(entity.get());
        return positionDTO;
    }

    public PositionDTO save(PositionDTO positionDTO) {
        Position entity = repository.save(new Position(positionDTO.getName(), positionDTO.getDescription()));
        positionDTO = new PositionDTO(entity);
        return positionDTO;
    }

    public PositionDTO update(Long id, PositionMinDTO obj) {
        Position entity = repository.getReferenceById(id);
        entity.updateData(obj);
        entity = repository.save(entity);
        PositionDTO entityDTO = new PositionDTO(entity);
        return entityDTO;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
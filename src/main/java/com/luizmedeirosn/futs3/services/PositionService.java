package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizmedeirosn.futs3.dto.request.post.PostPositionDTO;
import com.luizmedeirosn.futs3.dto.request.update.UpdatePositionDTO;
import com.luizmedeirosn.futs3.dto.response.PositionDTO;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.services.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.services.exceptions.EntityNotFoundException;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PositionDTO> findAll() {
        return positionRepository
            .findAll(Sort.by("id"))
            .stream()
            .map( PositionDTO::new )
            .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PositionDTO findById(Long id) {
        try {
            return new PositionDTO(positionRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Position ID not found");
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PositionDTO save(PostPositionDTO postPositionDTO) {
        try {
            Position position = positionRepository.save(new Position(postPositionDTO.getName(), postPositionDTO.getDescription()));
            return new PositionDTO(position);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Position request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Position request. The given IDs must not be null");
        
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Position request. Unique index, check index or primary key violation");
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PositionDTO update(Long id, UpdatePositionDTO updatePositionDTO) {
        try {
            Position position = positionRepository.getReferenceById(id);
            position.updateData(updatePositionDTO);
            position = positionRepository.save(position);
            return new PositionDTO(position);

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Position request. ID not found");
        
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Position request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        try {
            if (!positionRepository.existsById(id)) {
                throw new EntityNotFoundException("Position request. ID not found");
            }
            positionRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Position request. Database integrity reference constraint error");
        }
    }
    
}
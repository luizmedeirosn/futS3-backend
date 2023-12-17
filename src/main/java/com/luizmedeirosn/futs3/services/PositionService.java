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

import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.post.PostPositionDTO;
import com.luizmedeirosn.futs3.shared.dto.request.update.UpdatePositionDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionParameterRepository positionParameterRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PositionMinDTO> findAll() {
        return positionRepository
                .findAll(Sort.by("id"))
                .stream()
                .map(PositionMinDTO::new)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PositionMinDTO findById(Long id) {
        try {
            return new PositionMinDTO(positionRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Position ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PositionParametersProjection> findAllPositionParameters(Long id) {
        try {
            return positionParameterRepository.findAllPositionParameters(id);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Position ID not found");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PositionMinDTO save(PostPositionDTO postPositionDTO) {
        try {
            Position position = positionRepository
                    .save(new Position(postPositionDTO.getName(), postPositionDTO.getDescription()));
            return new PositionMinDTO(position);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Position request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Position request. The given IDs must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Position request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PositionMinDTO update(Long id, UpdatePositionDTO updatePositionDTO) {
        try {
            Position position = positionRepository.getReferenceById(id);
            position.updateData(updatePositionDTO);
            position = positionRepository.save(position);
            return new PositionMinDTO(position);

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
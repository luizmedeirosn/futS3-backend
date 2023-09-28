package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.input.post.PostParameterDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdateParameterDTO;
import com.luizmedeirosn.futs3.dto.output.ParameterDTO;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.projections.PlayerParameterProjection;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.services.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.services.exceptions.EntityNotFoundException;

@Service
public class ParameterSerivce {

    @Autowired
    private ParameterRepository parameterRepository;

    public List<ParameterDTO> findAll() {
        return parameterRepository
            .findAll(Sort.by("name"))
            .stream()
            .map( x -> new ParameterDTO(x) )
            .toList();
    }

    public ParameterDTO findById(Long id) {
        try {
            return new ParameterDTO(parameterRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Parameter ID not found");
        }
    }

    public List<PlayerParameterProjection> findByPlayerId(Long id) {
        try {
            return parameterRepository.findByPlayerId(id);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Parameter ID not found");
        }
    }

    public ParameterDTO save(PostParameterDTO parameterInputDTO) {
        try {
            Parameter parameter = new Parameter();
            parameter.setName(parameterInputDTO.getName());
            parameter.setDescription(parameterInputDTO.getDescription());
            parameter = parameterRepository.save(parameter);
            return new ParameterDTO(parameter);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Parameter request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Parameter request. The given IDs must not be null");
        
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Parameter request. Unique index, not null, check index or primary key violation");
        }
    }

    public ParameterDTO update(Long id, UpdateParameterDTO updateParameterDTO) {
        try {
            Parameter parameter = parameterRepository.getReferenceById(id);
            parameter.updateData(updateParameterDTO);
            parameter = parameterRepository.save(parameter);
            return new ParameterDTO(parameter);
        }
        catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Parameter request. IDs not found");
        
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Parameter request. Unique index, not null, check index or primary key violation");
        }
    }

    public void deleteById(Long id) {
        try {
            if (!parameterRepository.existsById(id)) {
                throw new EntityNotFoundException("Parameter request. ID not found");
            }
            parameterRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Parameter request. Database integrity reference constraint error");
        }
    }

}
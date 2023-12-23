package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.shared.dto.request.ParameterRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.ParameterResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParameterSerivce {

    private final ParameterRepository parameterRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ParameterResponseDTO> findAll() {
        return parameterRepository
                .findAll(Sort.by("name"))
                .stream()
                .map(ParameterResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ParameterResponseDTO findById(Long id) {
        try {
            return new ParameterResponseDTO(parameterRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Parameter ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PlayerParameterProjection> findByPlayerId(Long id) {
        try {
            return parameterRepository.findParametersByPlayerId(id);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Parameter ID not found");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public ParameterResponseDTO save(ParameterRequestDTO parameterRequestDTO) {
        try {
            Parameter parameter = new Parameter();
            parameter.setName(parameterRequestDTO.getName());
            parameter.setDescription(parameterRequestDTO.getDescription());
            parameter = parameterRepository.save(parameter);
            return new ParameterResponseDTO(parameter);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Parameter request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Parameter request. The given IDs must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(
                    "Parameter request. Unique index, not null, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public ParameterResponseDTO update(Long id, ParameterRequestDTO parameterRequestDTO) {
        try {
            Parameter parameter = parameterRepository.getReferenceById(id);
            parameter.updateData(parameterRequestDTO);
            parameter = parameterRepository.save(parameter);
            return new ParameterResponseDTO(parameter);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Parameter request. ID not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Parameter request. Unique index, check index or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
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
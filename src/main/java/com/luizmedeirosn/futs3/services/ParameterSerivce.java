package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.shared.dto.request.ParameterRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.ParameterResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParameterSerivce {

    private final ParameterRepository parameterRepository;

    public ParameterSerivce(
            ParameterRepository parameterRepository
    ) {
        this.parameterRepository = parameterRepository;
    }

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
        var parameter = parameterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parameter ID not found: " + id));
        return new ParameterResponseDTO(parameter);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public ParameterResponseDTO save(ParameterRequestDTO parameterRequestDTO) {
        try {
            Parameter parameter = new Parameter(parameterRequestDTO.name(), parameterRequestDTO.description());
            parameter = parameterRepository.save(parameter);
            return new ParameterResponseDTO(parameter);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public ParameterResponseDTO update(Long id, ParameterRequestDTO parameterRequestDTO) {
        try {
            Parameter parameter = parameterRepository.getReferenceById(id);
            parameter.updateData(parameterRequestDTO);

            parameter = parameterRepository.save(parameter);

            return new ParameterResponseDTO(parameter);

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Parameter ID not found: " + id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        try {
            if (!parameterRepository.existsById(id)) {
                throw new EntityNotFoundException("Parameter ID not found: " + id);
            }

            parameterRepository.customDeleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
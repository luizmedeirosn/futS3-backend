package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PlayerParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;
import com.luizmedeirosn.futs3.shared.dto.request.ParameterRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.ParameterResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ParameterSerivce {

    private final PositionParameterRepository positionParameterRepository;
    private final PlayerParameterRepository playerParameterRepository;
    private final ParameterRepository parameterRepository;

    public ParameterSerivce(
            PositionParameterRepository positionParameterRepository,
            PlayerParameterRepository playerParameterRepository,
            ParameterRepository parameterRepository
    ) {
        this.positionParameterRepository = positionParameterRepository;
        this.playerParameterRepository = playerParameterRepository;
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
    public ParameterResponseDTO findById(@NonNull Long id) {
        var parameter = parameterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parameter ID not found: " + id));
        return new ParameterResponseDTO(parameter);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public ParameterResponseDTO save(ParameterRequestDTO parameterRequestDTO) {
        try {
            Parameter parameter = new Parameter();
            parameter.setName(parameterRequestDTO.name());
            parameter.setDescription(parameterRequestDTO.description());
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public ParameterResponseDTO update(@NonNull Long id, ParameterRequestDTO parameterRequestDTO) {
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(@NonNull Long id) {
        try {
            if (!parameterRepository.existsById(id)) {
                throw new EntityNotFoundException("Parameter request. ID not found");
            }

            positionParameterRepository.deleteByParameterId(id);
            playerParameterRepository.deleteByParameterId(id);
            parameterRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Parameter request. Database integrity reference constraint error");
        }
    }
}
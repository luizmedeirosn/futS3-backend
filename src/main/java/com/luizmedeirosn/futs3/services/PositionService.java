package com.luizmedeirosn.futs3.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.PositionRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.aux.ParameterWeightDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PositionResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final ParameterRepository parameterRepository;
    private final PositionParameterRepository positionParameterRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PositionMinDTO> findAll() {
        return positionRepository
                .findAll(Sort.by("name"))
                .stream()
                .map(PositionMinDTO::new)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PositionMinDTO findById(@NonNull Long id) {
        try {
            return new PositionMinDTO(positionRepository.findById(id).get());

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Position ID not found");
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PositionResponseDTO> findAllWithParameters() {
        List<PositionResponseDTO> result = new ArrayList<>();
        positionRepository.findAll(Sort.by("name")).forEach(position -> result.add(new PositionResponseDTO(position,
                positionParameterRepository.findByIdPositionParameters(position.getId()))));

        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PositionResponseDTO findByIdPositionParameters(@NonNull Long id) {
        try {
            if (!positionRepository.existsById(id)) {
                throw new NoSuchElementException();
            }
            Position position = positionRepository.getReferenceById(id);
            return new PositionResponseDTO(position.getId(), position.getName(), position.getDescription(),
                    positionParameterRepository.findByIdPositionParameters(id));

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Position ID not found");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PositionMinDTO save(PositionRequestDTO positionRequestDTO) {
        try {
            Position position = positionRepository
                    .save(new Position(positionRequestDTO.name(), positionRequestDTO.description()));

            positionRequestDTO.parameters().forEach(parameterWeight -> {
                Long parameterId = parameterWeight.id();
                if (parameterId != null) {
                    PositionParameter positionParameter = new PositionParameter();

                    positionParameter.setPosition(position);
                    positionParameter.setParameter(parameterRepository.findById(parameterId)
                            .orElseThrow(NoSuchElementException::new));
                    positionParameter.setWeight(parameterWeight.weight());

                    positionParameterRepository.save(positionParameter);
                } else {
                    throw new InvalidDataAccessApiUsageException("");
                }
            });
            return new PositionMinDTO(position);

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Position request. IDs not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Position request. The given IDs must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Position request. Unique index, check index or primary key violation");
        }
    }

    @SuppressWarnings("java:S2583")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PositionMinDTO update(@NonNull Long id, PositionRequestDTO positionRequestDTO) {
        try {
            Position position = positionRepository.getReferenceById(id);
            position.updateData(positionRequestDTO);

            positionParameterRepository.deleteByIdPositionId(id);

            for (ParameterWeightDTO parameterWeight : positionRequestDTO.parameters()) {
                Long parameterId = parameterWeight.id();
                if (parameterId != null) {
                    PositionParameter positionParameter = new PositionParameter();
                    positionParameter.setPosition(position);

                    Parameter parameter = parameterRepository.findById(parameterId)
                            .orElseThrow(NoSuchElementException::new);

                    positionParameter.setParameter(parameter);
                    positionParameter.setWeight(parameterWeight.weight());

                    positionParameterRepository.save(positionParameter);
                } else {
                    throw new InvalidDataAccessApiUsageException("");
                }
            }

            position = positionRepository.save(position);
            return new PositionMinDTO(position);

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Position request. ID not found");

        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Position request. The given ID must not be null");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Position request. Unique index, check index, or primary key violation");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteById(@NonNull Long id) {
        try {
            if (!positionRepository.existsById(id)) {
                throw new EntityNotFoundException("Position request. ID not found");
            }
            positionRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
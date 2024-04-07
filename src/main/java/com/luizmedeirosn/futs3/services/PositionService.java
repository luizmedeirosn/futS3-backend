package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.PositionRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.aux.ParameterWeightDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;
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
@SuppressWarnings({"java:S2583"})
public class PositionService {

    private final PositionRepository positionRepository;
    private final ParameterRepository parameterRepository;
    private final PositionParameterRepository positionParameterRepository;

    public PositionService(PositionRepository positionRepository, ParameterRepository parameterRepository, PositionParameterRepository positionParameterRepository) {
        this.positionRepository = positionRepository;
        this.parameterRepository = parameterRepository;
        this.positionParameterRepository = positionParameterRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<PositionMinDTO> findAll() {
        return positionRepository
                .findAll(Sort.by("name"))
                .stream()
                .map(PositionMinDTO::new)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PositionMinDTO findById(Long id) {
        return new PositionMinDTO(positionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Position ID not found: " + id)));
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
                    throw new NullPointerException();
                }
            });

            return new PositionMinDTO(position);

        } catch (NullPointerException | InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Position request. The given ID must not be null");

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Position request. ID not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Position request. Unique index, check index, or primary key violation");
        }
    }

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
                    throw new NullPointerException();
                }
            }

            position = positionRepository.save(position);
            return new PositionMinDTO(position);

        } catch (NullPointerException | InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Position request. The given ID must not be null");

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new EntityNotFoundException("Position request. ID not found");

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
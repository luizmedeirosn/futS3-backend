package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;
import com.luizmedeirosn.futs3.repositories.PositionRepository;
import com.luizmedeirosn.futs3.shared.dto.request.PositionRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PositionParameterIdWeightDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PositionResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PositionParametersDataDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;
import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService {

  private final PositionRepository positionRepository;
  private final PositionParameterRepository positionParameterRepository;
  private final EntityManager entityManager;

  public PositionService(
      PositionRepository positionRepository,
      PositionParameterRepository positionParameterRepository,
      EntityManager entityManager) {
    this.positionRepository = positionRepository;
    this.positionParameterRepository = positionParameterRepository;
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Page<PositionMinDTO> findAll(Pageable pageable) {
    return positionRepository.findAll(pageable).map(PositionMinDTO::new);
  }

  public Long getTotalRecords() {
    return positionRepository.count();
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public PositionResponseDTO findById(Long id) {
    try {
      var projections = positionRepository.customFindById(id);
      return new PositionResponseDTO(projections.get(0), extractPositionParameters(projections));
    } catch (Exception e) {
      throw new EntityNotFoundException("Position ID not found: " + id);
    }
  }

  private List<PositionParametersDataDTO> extractPositionParameters(List<PositionParametersProjection> projections) {
    if (projections.get(0).getParameterId() != null) {
      return projections.stream()
          .map(
              p -> {
                long parameterId = p.getParameterId();
                String parameterName = p.getParameterName();
                int positionWeight = p.getPositionWeight();
                return new PositionParametersDataDTO(parameterId, parameterName, positionWeight);
              })
          .toList();
    }
    return new ArrayList<>();
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public PositionResponseDTO save(PositionRequestDTO positionRequestDTO) {
    try {
      if (sumWeights(positionRequestDTO.parameters()) > 100) {
        throw new DataIntegrityViolationException("The sum of weights cannot exceed 100");
      } else if (positionRequestDTO.parameters().size() > 10) {
        throw new DataIntegrityViolationException("The maximum allowed number of parameters for a position is 10");
      }

      var newPosition = new Position(positionRequestDTO);
      positionRepository.save(newPosition);

      positionParameterRepository.saveAllPositionParameters(
          newPosition.getId(), positionRequestDTO.parameters(), entityManager);

      return findById(newPosition.getId());
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException(e.getMessage());
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public PositionResponseDTO update(Long id, PositionRequestDTO positionRequestDTO) {
    try {
      if (sumWeights(positionRequestDTO.parameters()) > 100) {
        throw new DataIntegrityViolationException("The sum of weights cannot exceed 100");
      } else if (positionRequestDTO.parameters().size() > 10) {
        throw new DataIntegrityViolationException("The maximum allowed number of parameters for a position is 10");
      }

      var position = positionRepository.getReferenceById(id);
      position.updateData(positionRequestDTO);

      positionParameterRepository.deleteByPositionId(position.getId());
      positionParameterRepository.saveAllPositionParameters(
          position.getId(), positionRequestDTO.parameters(), entityManager);

      position = positionRepository.save(position);
      return findById(position.getId());
    } catch (jakarta.persistence.EntityNotFoundException e) {
      throw new EntityNotFoundException("Position ID not found: " + id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException(e.getMessage());
    }
  }

  private int sumWeights(List<PositionParameterIdWeightDTO> weights) {
    return weights.stream().mapToInt(PositionParameterIdWeightDTO::weight).sum();
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void deleteById(Long id) {
    try {
      if (!positionRepository.existsById(id)) throw new EntityNotFoundException("Position ID not found: " + id);
      positionRepository.customDeleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException(e.getMessage());
    }
  }
}

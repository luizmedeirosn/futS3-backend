package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.entities.pks.PositionParameterPK;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PositionParameterIdWeightDTO;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PositionParameterRepository extends JpaRepository<PositionParameter, PositionParameterPK> {

  @Modifying
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  @Query("DELETE FROM PositionParameter pp WHERE pp.id.position.id = :id")
  void deleteByPositionId(@Param(value = "id") Long id);

  @Modifying
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  default void saveAllPositionParameters(
      Long positionId, List<PositionParameterIdWeightDTO> parameters, EntityManager entityManager) {
    StringBuilder queryStr = new StringBuilder();

    int end = parameters.size();
    for (int i = 0; i < end; i++) {
      var parameter = parameters.get(i);
      if (i == 0) {
        queryStr.append("INSERT INTO tb_position_parameter (position_id, parameter_id, weight) VALUES");
        queryStr.append(String.format(" (%d, %d, %d)", positionId, parameter.id(), parameter.weight()));
      } else {
        queryStr.append(String.format(", (%d, %d, %d)", positionId, parameter.id(), parameter.weight()));
      }
    }

    jakarta.persistence.Query query = entityManager.createNativeQuery(queryStr.toString());
    query.executeUpdate();
  }
}

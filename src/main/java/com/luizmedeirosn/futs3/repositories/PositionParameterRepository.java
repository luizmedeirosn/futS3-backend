package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.entities.pks.PositionParameterPK;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PositionParameterIdWeightDTO;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PositionParameterRepository extends JpaRepository<PositionParameter, PositionParameterPK> {

    void deleteByIdPositionId(Long positionId);

    void deleteByIdParameterId(Long parameterId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    default void customSaveAll(EntityManager entityManager, Long positionId, List<PositionParameterIdWeightDTO> parameters) {
        StringBuilder queryStr = new StringBuilder();

        int end = parameters.size();
        for (int i = 0; i < end; i++) {
            if (i == 0) {
                queryStr.append("INSERT INTO tb_position_parameter (position_id, parameter_id, weight) VALUES (?, ?, ?)");
            } else {
                queryStr.append(", (?, ?, ?)");
            }
        }

        jakarta.persistence.Query query = entityManager.createNativeQuery(queryStr.toString());

        int parameterIndex = 1;
        for (PositionParameterIdWeightDTO parameter : parameters) {
            query.setParameter(parameterIndex++, positionId);
            query.setParameter(parameterIndex++, parameter.id());
            query.setParameter(parameterIndex++, parameter.weight());
        }

        query.executeUpdate();
    }
}

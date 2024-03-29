package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.entities.pks.PlayerParameterPK;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterIdScoreDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlayerParameterRepository extends JpaRepository<PlayerParameter, PlayerParameterPK> {

    void deleteByIdPlayerId(Long playerId);
    void deleteByIdParameterId(Long parameterId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    default void saveAllOptimized(EntityManager entityManager, Long playerId, List<PlayerParameterIdScoreDTO> parameters) {
        StringBuilder queryStr = new StringBuilder();

        int end = parameters.size();
        for (int i = 0; i < end; i++) {
            if (i == 0) {
                queryStr.append("INSERT INTO tb_player_parameter (player_id, parameter_id, score) VALUES (?, ?, ?), ");
            } else if (i < end - 1) {
                queryStr.append("(?, ?, ?), ");
            } else {
                queryStr.append("(?, ?, ?)");
            }
        }

        Query query = entityManager.createNativeQuery(queryStr.toString());

        int parameterIndex = 1;
        for (PlayerParameterIdScoreDTO parameter : parameters) {
            query.setParameter(parameterIndex++, playerId);
            query.setParameter(parameterIndex++, parameter.id());
            query.setParameter(parameterIndex++, parameter.score());
        }

        query.executeUpdate();
    }
}

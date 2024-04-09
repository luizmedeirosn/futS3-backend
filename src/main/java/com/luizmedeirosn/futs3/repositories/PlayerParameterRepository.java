package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.entities.pks.PlayerParameterPK;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterIdScoreDTO;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlayerParameterRepository extends JpaRepository<PlayerParameter, PlayerParameterPK> {

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Query(nativeQuery = true, value = "DELETE FROM tb_player_parameter WHERE player_id = :id ;")
    void deleteByPlayerId(@Param("id") Long id);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    default void saveAllPlayerParameters(Long playerId, List<PlayerParameterIdScoreDTO> parameters, EntityManager entityManager) {
        StringBuilder queryStr = new StringBuilder();

        int end = parameters.size();
        for (int i = 0; i < end; i++) {
            var parameter = parameters.get(i);
            if (i == 0) {
                queryStr.append("INSERT INTO tb_player_parameter (player_id, parameter_id, score) VALUES");
                queryStr.append(String.format(" (%d, %d, %d)", playerId, parameter.id(), parameter.score()));
            } else {
                queryStr.append(String.format(", (%d, %d, %d)", playerId, parameter.id(), parameter.score()));
            }
        }

        jakarta.persistence.Query query = entityManager.createNativeQuery(queryStr.toString());
        query.executeUpdate();
    }
}

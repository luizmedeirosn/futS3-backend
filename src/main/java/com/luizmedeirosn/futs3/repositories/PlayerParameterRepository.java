package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.entities.pks.PlayerParameterPK;
import com.luizmedeirosn.futs3.projections.player.PlayerIdParameterProjection;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterIdScoreDTO;
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
public interface PlayerParameterRepository extends JpaRepository<PlayerParameter, PlayerParameterPK> {

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @Query(
      nativeQuery = true,
      value =
          """
            SELECT
              PLAY.id AS playerId,
              PARAM.id AS parameterId,
              PARAM.name AS parameterName,
              PLAYPARAM.score AS playerScore
            FROM
              tb_player_parameter AS PLAYPARAM
                INNER JOIN tb_player AS PLAY ON PLAYPARAM.player_id = PLAY.id
                INNER JOIN tb_parameter AS PARAM  ON PLAYPARAM.parameter_id = PARAM.id
                INNER JOIN tb_position_parameter AS POSPARAM ON PARAM.id = POSPARAM.parameter_id
            WHERE
              PLAY.id IN :ids AND POSPARAM.position_id = PLAY.position_id
            ORDER BY param.name;
          """)
  List<PlayerIdParameterProjection> findParametersByPlayerIds(@Param(value = "ids") List<Long> ids);

  @Modifying
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  @Query(nativeQuery = true, value = "DELETE FROM tb_player_parameter WHERE player_id = :id ;")
  void deleteByPlayerId(@Param("id") Long id);

  @Modifying
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  default void saveAllPlayerParameters(
      Long playerId, List<PlayerParameterIdScoreDTO> parameters, EntityManager entityManager) {
    var queryStr = new StringBuilder();

    for (int i = 0; i < parameters.size(); i++) {
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

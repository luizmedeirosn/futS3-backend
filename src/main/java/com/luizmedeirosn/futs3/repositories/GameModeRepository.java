package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerDataScoreProjection;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
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
public interface GameModeRepository extends JpaRepository<GameMode, Long> {

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @Query(
      nativeQuery = true,
      value =
          """
            SELECT
              pos.id,
              pos.name,
              pos.description,
              param.id AS parameterId,
              param.name AS parameterName,
              posparam.weight AS positionWeight
            FROM
              tb_game_mode_position AS gamepos
                INNER JOIN tb_position AS pos ON gamepos.position_id = pos.id
                LEFT JOIN tb_position_parameter AS posparam ON pos.id = posparam.position_id
                LEFT JOIN tb_parameter AS param ON posparam.parameter_id = param.id
            WHERE gamepos.game_mode_id = :id
            ORDER BY pos.name, param.name;
          """)
  List<PositionParametersProjection> findPositionsDataByGameModeId(@Param("id") Long id);

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @Query(
      nativeQuery = true,
      value =
          """
            SELECT
              PLAY.id AS playerId,
              PLAY.name AS playerName,
              PLAYPIC.content AS playerProfilePicture,
              PLAY.age AS playerAge,
              PLAY.height AS playerHeight,
              PLAY.team AS playerTeam,
              SUM(PLAYPARAM.score * POSPARAM.weight) AS totalScore
            FROM
              tb_position_parameter AS POSPARAM
                INNER JOIN tb_game_mode_position AS GAMEPOS ON POSPARAM.position_id = GAMEPOS.position_id
                INNER JOIN tb_player AS PLAY ON POSPARAM.position_id = PLAY.position_id
                INNER JOIN tb_parameter AS PARAM ON POSPARAM.parameter_id = PARAM.id
                INNER JOIN tb_player_parameter AS PLAYPARAM ON PLAY.id = PLAYPARAM.player_id
                LEFT JOIN tb_player_picture AS PLAYPIC ON PLAY.id = PLAYPIC.player_id
            WHERE
              GAMEPOS.game_mode_id = :gameModeId AND
              POSPARAM.position_id = :positionId
            GROUP BY
              PLAY.id, PLAY.name, PLAYPIC.content, PLAY.age, PLAY.height, PLAY.team
            ORDER BY totalScore DESC
            OFFSET :offset
            FETCH FIRST :pageSize ROWS ONLY;
          """)
  List<PlayerDataScoreProjection> getPlayersRanking(
      @Param(value = "gameModeId") Long gameModeId,
      @Param(value = "positionId") Long positionId,
      @Param(value = "offset") Long offset,
      @Param(value = "pageSize") Integer pageSize);

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @Query(
      nativeQuery = true,
      value =
          """
            SELECT
              COUNT(DISTINCT PLAY.id)
            FROM
              tb_position_parameter AS POSPARAM
                INNER JOIN tb_game_mode_position AS GAMEPOS ON POSPARAM.position_id = GAMEPOS.position_id
                INNER JOIN tb_player AS PLAY ON POSPARAM.position_id = PLAY.position_id
                INNER JOIN tb_parameter AS PARAM ON POSPARAM.parameter_id = PARAM.id
                INNER JOIN tb_player_parameter AS PLAYPARAM ON PLAY.id = PLAYPARAM.player_id
            WHERE
              GAMEPOS.game_mode_id = :gameModeId AND
              POSPARAM.position_id = :positionId AND
              PLAYPARAM.parameter_id = POSPARAM.parameter_id;
          """)
  Long countGetPlayersRanking(
      @Param(value = "gameModeId") Long gameModeId, @Param(value = "positionId") Long positionId);

  @Modifying
  @Query(nativeQuery = true, value = "DELETE FROM tb_game_mode_position AS gmp WHERE gmp.game_mode_id = :id ;")
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  void deletePositionsFromGameModeById(@Param(value = "id") Long id);

  @Modifying
  @Query(
      nativeQuery = true,
      value =
          """
            DELETE FROM tb_game_mode_position AS gmp WHERE gmp.game_mode_id = :id ;
            DELETE FROM tb_game_mode AS gm WHERE gm.id = :id ;
          """)
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  void customDeleteById(Long id);

  @Modifying
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  default void saveAllPositions(Long gameModeId, List<Long> positions, EntityManager entityManager) {
    var queryStr = new StringBuilder();

    int parameterIndex = 1;
    for (int i = 0; i < positions.size(); i++) {
      if (i == 0) {
        queryStr.append("INSERT INTO tb_game_mode_position (game_mode_id, position_id) VALUES");
        queryStr.append(String.format(" (%d, %d)", gameModeId, positions.get(i)));
      } else {
        queryStr.append(String.format(", (%d, %d)", gameModeId, positions.get(i)));
      }
    }

    jakarta.persistence.Query query = entityManager.createNativeQuery(queryStr.toString());
    query.executeUpdate();
  }
}

package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
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
public interface PlayerRepository extends JpaRepository<Player, Long> {

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @Query(
      nativeQuery = true,
      value =
          """
            SELECT
              PLAY.id AS playerId,
              PLAY.name AS playerName,
              PLAY.age AS playerAge,
              PLAY.height AS playerHeight,
              PLAY.team AS playerTeam,
              PLAYPIC.content AS playerPicture,
              POS.id AS positionId,
              POS.name AS positionName,
              POS.description AS positionDescription
            FROM
              tb_player AS PLAY
              INNER JOIN tb_position AS POS ON PLAY.position_id = POS.id
              LEFT JOIN tb_player_picture AS PLAYPIC ON PLAY.id = PLAYPIC.player_id
            WHERE
              LOWER(PLAY.name) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR
              LOWER(PLAY.team) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR
              LOWER(POS.name) LIKE LOWER(CONCAT('%', :keyword ,'%'))
            ORDER BY PLAY.name
            OFFSET :offset ROWS
            FETCH FIRST :pageSize ROWS ONLY;
          """)
  List<PlayerProjection> customFindAll(
      @Param(value = "keyword") String keyword,
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
              tb_player AS PLAY
                INNER JOIN tb_position AS POS ON PLAY.position_id = POS.id
                LEFT JOIN tb_player_picture AS PLAYPIC ON PLAY.id = PLAYPIC.player_id
            WHERE
              LOWER(PLAY.name) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR
              LOWER(PLAY.team) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR
              LOWER(POS.name) LIKE LOWER(CONCAT('%', :keyword ,'%'));
          """)
  Long countCustomFindAll(@Param(value = "keyword") String keyword);

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @Query(
      nativeQuery = true,
      value =
          """
            SELECT
              PLAY.id AS playerId,
              PLAY.name AS playerName,
              PLAY.age AS playerAge,
              PLAY.height AS playerHeight,
              PLAY.team AS playerTeam,
              PLAYPIC.content AS playerPicture,
              POS.id AS positionId,
              POS.name AS positionName,
              POS.description AS positionDescription,
              PARAM.id AS parameterId,
              PARAM.name AS parameterName,
              PLAYPARAM.score AS playerScore
            FROM
              tb_player AS PLAY
                INNER JOIN tb_position AS POS ON PLAY.position_id = POS.id
                LEFT JOIN tb_player_parameter AS PLAYPARAM ON PLAY.id = PLAYPARAM.player_id
                LEFT JOIN tb_parameter AS PARAM ON playparam.parameter_id = PARAM.id
                LEFT JOIN tb_player_picture AS PLAYPIC ON PLAY.id = PLAYPIC.player_id
            WHERE PLAY.id = :id
            ORDER BY PARAM.name;
          """)
  List<PlayerProjection> customFindById(@Param(value = "id") Long id);

  @Modifying
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  @Query(
      nativeQuery = true,
      value =
          """
            DELETE FROM tb_player_parameter WHERE player_id = :id ;
            DELETE FROM tb_player_picture WHERE player_id = :id ;
            DELETE FROM tb_player WHERE id = :id ;
          """)
  void customDeleteById(@Param(value = "id") Long id);
}

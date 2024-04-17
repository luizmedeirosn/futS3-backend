package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerDataScoreProjection;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
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
public interface GameModeRepository extends JpaRepository<GameMode, Long> {

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Query(nativeQuery = true, value = """
                    SELECT
                        pos.id,
                        pos.name,
                        pos.description,
                        param.id AS parameterId,
                        param.name AS parameterName,
                        posparam.weight AS positionWeight
                    FROM
                        tb_game_mode_position AS gamepos
                            INNER JOIN tb_position AS pos
                                ON gamepos.position_id = pos.id
                            LEFT JOIN tb_position_parameter AS posparam
                                ON pos.id = posparam.position_id
                            LEFT JOIN tb_parameter AS param
                                ON posparam.parameter_id = param.id
                    WHERE gamepos.game_mode_id = :id
                    ORDER BY pos.name, param.name;
            """)
    List<PositionParametersProjection> findPositionsDataByGameModeId(@Param("id") Long id);

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Query(nativeQuery = true, value = """
                    SELECT
                        player_id AS playerId,
                        player_name AS playerName,
                        player_picture AS playerProfilePicture,
                        player_age AS playerAge,
                        player_height AS playerHeight,
                        player_team AS playerTeam,
                        SUM(player_score * param_weight) AS totalScore
                    FROM (
                        SELECT
                            PLAY.id AS player_id,
                            PLAY.name AS player_name,
                            PLAYPIC.content AS player_picture,
                            PLAY.age AS player_age,
                            PLAY.height AS player_height,
                            PLAY.team AS player_team,
                            PARAM.id AS parameter_id,
                            PARAM.name AS parameter_name,
                            PLAYPARAM.score AS player_score,
                            POSPARAM.weight AS param_weight
                        FROM
                            tb_position_parameter AS POSPARAM
                                INNER JOIN tb_game_mode_position AS GAMEPOS
                                    ON POSPARAM.position_id = GAMEPOS.position_id
                                INNER JOIN tb_player AS PLAY
                                    ON POSPARAM.position_id = PLAY.position_id
                                INNER JOIN tb_parameter AS PARAM
                                    ON POSPARAM.parameter_id = PARAM.id
                                INNER JOIN tb_player_parameter AS PLAYPARAM
                                    ON PLAY.id = PLAYPARAM.player_id
                                LEFT JOIN tb_player_picture AS PLAYPIC
                                    ON play.id = playpic.player_id
                        WHERE
                            GAMEPOS.game_mode_id = :gameModeId AND
                            POSPARAM.position_id = :positionId AND
                            PLAYPARAM.parameter_id = POSPARAM.parameter_id
                    ) subquery
                    GROUP BY player_id, player_name, player_picture, player_age, player_height, player_team
                    ORDER BY totalScore DESC;
            """)
    List<PlayerDataScoreProjection> getPlayersRanking(Long gameModeId, Long positionId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_game_mode_position AS gmp WHERE gmp.game_mode_id = :id ;")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    void deletePositionsFromGameModeById(Long id);

    @Modifying
    @Query(nativeQuery = true, value = """
                DELETE FROM tb_game_mode_position AS gmp WHERE gmp.game_mode_id = :id ;
                DELETE FROM tb_game_mode AS gm WHERE gm.id = :id ;
            """)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    void customDeleteById(Long id);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    default void saveAllPositions(Long gameModeId, List<Long> positions, EntityManager entityManager) {
        StringBuilder queryStr = new StringBuilder();

        int end = positions.size();
        int parameterIndex = 1;
        for (int i = 0; i < end; i++) {
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
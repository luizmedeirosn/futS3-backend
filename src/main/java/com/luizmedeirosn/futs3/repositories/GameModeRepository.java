package com.luizmedeirosn.futs3.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.projections.gamemode.AllGameModesProjection;
import com.luizmedeirosn.futs3.projections.gamemode.GameModePositionProjection;
import com.luizmedeirosn.futs3.projections.gamemode.GameModePositionsParametersProjection;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerFullScoreProjection;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_gamemode_position WHERE gamemode_id = :id\\;")
    void deleteByIdFromTbGameModePosition(Long id);

    @Query(nativeQuery = true, value = """
                SELECT
                    POS.id AS positionId,
                    POS.name AS positionName
                FROM
                    tb_gamemode_position AS GAMEPOS
                        INNER JOIN tb_position AS POS
                            ON GAMEPOS.position_id = POS.id
                WHERE
                    GAMEPOS.gamemode_id = :id\\;
            """)
    Optional<List<GameModePositionProjection>> findGameModePositions(Long id);

    @Query(nativeQuery = true, value = """
                SELECT
                    gamemodepos.gamemode_id AS gameModeId,
                    gamemodepos.gamemode_formation_name AS gameModeFormationName,
                    gamemodepos.position_id AS positionId,
                    gamemodepos.position_name AS positionName,
                    posparam.parameter_id AS parameterId,
                    param.name AS parameterName,
                    posparam.weight AS parameterWeight
                FROM
                    tb_position_parameter AS posparam
                    INNER JOIN (
                        SELECT
                            gamemode.id AS gamemode_id,
                            gamemode.formation_name AS gamemode_formation_name,
                            pos.id AS position_id,
                            pos.name AS position_name

                        FROM
                            tb_gamemode_position AS gamepos
                            INNER JOIN tb_gamemode AS gamemode
                                ON gamepos.gamemode_id = gamemode.id
                            INNER JOIN tb_position AS pos
                                ON gamepos.position_id = pos.id
                    ) AS gamemodepos
                        ON posparam.position_id = gamemodepos.position_id
                    INNER JOIN tb_parameter AS param
                        ON posparam.parameter_id = param.id;
            """)
    List<AllGameModesProjection> findAllFull();

    @Query(nativeQuery = true, value = """
                SELECT
                    gamemodepos.position_id AS positionId,
                    gamemodepos.position_name AS positionName,
                    posparam.parameter_id AS parameterId,
                    param.name AS parameterName,
                    posparam.weight AS parameterWeight
                FROM
                    tb_position_parameter AS posparam
                        INNER JOIN (
                            SELECT
                                pos.id AS position_id,
                                pos.name AS position_name
                            FROM
                                tb_gamemode_position AS gamepos
                                    INNER JOIN tb_gamemode AS gamemode
                                        ON gamepos.gamemode_id = gamemode.id
                                    INNER JOIN tb_position AS pos
                                        ON gamepos.position_id = pos.id
                            WHERE gamemode.id = :gameModeId
                        ) AS gamemodepos
                            ON posparam.position_id = gamemodepos.position_id
                        INNER JOIN tb_parameter AS param
                            ON posparam.parameter_id = param.id;
            """)
    List<GameModePositionsParametersProjection> findByIdWithPositionsParameters(Long gameModeId);

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
                                INNER JOIN tb_gamemode_position AS GAMEPOS
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
                            GAMEPOS.gamemode_id = :gameModeId AND
                            POSPARAM.position_id = :positionId AND
                            PLAYPARAM.parameter_id = POSPARAM.parameter_id
                    ) subquery
                    GROUP BY player_id, player_name, player_picture, player_age, player_height, player_team
                    ORDER BY totalScore DESC;
            """)
    Optional<List<PlayerFullScoreProjection>> getPlayersRanking(Long gameModeId, Long positionId);

}
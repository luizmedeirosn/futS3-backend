package com.luizmedeirosn.futs3.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.projections.gamemode.AllGameModesProjection;
import com.luizmedeirosn.futs3.projections.gamemode.GameModeProjection;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerScoreProjection;

public interface GameModeRepository extends JpaRepository<GameMode, Long> {

    @Query (
        nativeQuery = true,
        value = """
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
        """
    ) List<AllGameModesProjection> findAllFull();

    @Query (
        nativeQuery = true,
        value = """
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
        """
    ) List<GameModeProjection> findFullById(Long gameModeId);

    @Query (
        nativeQuery = true,
        value = """
            SELECT
                player_id AS playerId, 
                player_name AS playerName,
                SUM(player_score * param_weight) AS totalScore
            FROM (
                SELECT
                    PLAY.id AS player_id,
                    PLAY.name AS player_name,
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
                WHERE
                    GAMEPOS.gamemode_id = :gameModeId AND
                    POSPARAM.position_id = :positionId AND
                    PLAYPARAM.parameter_id = POSPARAM.parameter_id
            ) subquery
            GROUP BY player_id, player_name
            ORDER BY totalScore DESC;      
    """
    ) Optional<List<PlayerScoreProjection>> getPlayerRanking(Long gameModeId, Long positionId);

}
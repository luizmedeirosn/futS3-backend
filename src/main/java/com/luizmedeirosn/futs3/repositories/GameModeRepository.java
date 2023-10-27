package com.luizmedeirosn.futs3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.projections.gamemode.AllGameModesProjection;
import com.luizmedeirosn.futs3.projections.gamemode.GameModeProjection;

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

}
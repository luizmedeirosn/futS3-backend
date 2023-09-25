package com.luizmedeirosn.futs3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.projections.GameModeProjection;

public interface GameModeRepository extends JpaRepository<GameMode, Long> {

    @Query (
        nativeQuery = true,
        value = """
            select
                gamemodepos.position_id as positionId,
                gamemodepos.position_name as positionName,
                posparam.parameter_id as parameterId,
                param.name as parameterName,
                posparam.weight as parameterWeight
            from 
                tb_position_parameter as posparam
                    inner join (
                        select
                            pos.id as position_id,
                            pos.name as position_name
                        from
                            tb_gamemode_position as gamepos
                                inner join tb_gamemode as gamemode
                                    on gamepos.gamemode_id = gamemode.id
                                inner join tb_position as pos
                                    on gamepos.position_id = pos.id
                        where gamemode.id = :gameModeId
                    ) as gamemodepos
                        on posparam.position_id = gamemodepos.position_id
                    inner join tb_parameter as param
                        on posparam.parameter_id = param.id;
            """
    ) List<GameModeProjection> findCompleteGameModeById(Long gameModeId);

}

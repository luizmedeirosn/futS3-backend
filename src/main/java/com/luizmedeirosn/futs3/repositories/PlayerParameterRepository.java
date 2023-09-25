package com.luizmedeirosn.futs3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.projections.AllPlayersParametersProjection;

public interface PlayerParameterRepository extends JpaRepository<PlayerParameter, Long> {

    @Query (
            nativeQuery = true,
            value = """
                SELECT
                    play.id as playerId,
                    play.name as playerName,
                    param.id as parameterId, 
                    param.name as parameterName, 
                    playparam.score AS playerScore 
                FROM 
                    tb_player_parameter AS playparam 
                    INNER JOIN tb_player AS play 
                        ON playparam.player_id = play.id 
                    INNER JOIN tb_parameter AS param 
                        ON playparam.parameter_id = param.id 
                ORDER BY play.name;
            """
        ) List<AllPlayersParametersProjection> findAllPlayersParameters();

}

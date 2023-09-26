package com.luizmedeirosn.futs3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.projections.PlayerParameterProjection;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {

        @Query ( 
        nativeQuery = true,
        value = """
            SELECT 
                param.id, 
                param.name, 
                playparam.score AS playerScore
            FROM 
                tb_player_parameter AS playparam 
                INNER JOIN tb_player AS play 
                    ON playparam.player_id = play.id 
                INNER JOIN tb_parameter AS param 
                    ON playparam.parameter_id = param.id 
            WHERE play.id = :playerId 
            ORDER BY param.name;
        """
    ) List<PlayerParameterProjection> findByPlayerId(Long playerId);
    
}
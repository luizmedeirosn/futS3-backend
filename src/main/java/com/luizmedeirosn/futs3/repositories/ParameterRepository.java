package com.luizmedeirosn.futs3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.projections.ParameterProjection;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {

        @Query ( 
        nativeQuery = true,
        value = """
                SELECT 
                    PARAM.id, 
                    PARAM.name, 
                    PLAY_PARAM.score AS playerScore, 
                    PARAM.description, 
                FROM 
                tb_player_parameter AS PLAY_PARAM 
                    INNER JOIN tb_player AS PLAY 
                        ON PLAY_PARAM.player_id = PLAY.id 
                    INNER JOIN tb_parameter AS PARAM 
                        ON PLAY_PARAM.parameter_id = PARAM.id 
                WHERE PLAY.id = :playerId 
                ORDER BY PARAM.name;
                """
    ) List<ParameterProjection> findByPlayerId(Long playerId);
}

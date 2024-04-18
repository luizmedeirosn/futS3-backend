package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
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
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query(nativeQuery = true, value = """
                SELECT
                    param.id,
                    param.name,
                    playparam.score AS score
                FROM
                    tb_player_parameter AS playparam
                    INNER JOIN tb_player AS play
                        ON playparam.player_id = play.id
                    INNER JOIN tb_parameter AS param
                        ON playparam.parameter_id = param.id
                WHERE play.id = :id
                ORDER BY param.name;
            """)
    List<PlayerParameterProjection> findParametersByPlayerId(@Param(value = "id") Long id);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Query(nativeQuery = true, value = """
                DELETE FROM tb_position_parameter WHERE parameter_id = :id ;
                DELETE FROM tb_player_parameter WHERE parameter_id = :id ;
                DELETE FROM tb_parameter WHERE id = :id ;
            """)
    void customDeleteById(@Param("id") Long id);
}
package com.luizmedeirosn.futs3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.entities.pks.PositionParameterPK;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;

@Repository
public interface PositionParameterRepository extends JpaRepository<PositionParameter, PositionParameterPK> {

    void deleteByIdPositionId(Long positionId);

    void deleteByIdParameterId(Long parameterId);

    @Query(nativeQuery = true, value = """
                SELECT
                    param.id AS id,
                    param.name AS name,
                    posparam.weight AS weight
                FROM
                    tb_position_parameter as posparam
                        INNER JOIN tb_position AS pos
                            ON posparam.position_id = pos.id
                        INNER JOIN tb_parameter AS param
                            ON posparam.parameter_id = param.id
                WHERE
                    pos.id = :positionId
                ORDER BY name;
            """)
    List<PositionParametersProjection> findByIdPositionParameters(Long positionId);

}

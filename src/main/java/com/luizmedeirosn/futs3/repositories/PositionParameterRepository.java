package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.entities.pks.PositionParameterPK;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PositionParameterRepository extends JpaRepository<PositionParameter, PositionParameterPK> {

    void deleteByIdPositionId(Long positionId);
    void deleteByIdParameterId(Long parameterId);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
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
                    pos.id = :id
                ORDER BY name;
            """)
    List<PositionParametersProjection> findByIdPositionParameters(@Param("id") Long id);
}

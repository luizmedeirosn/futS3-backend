package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.entities.pks.PositionParameterPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionParameterRepository extends JpaRepository<PositionParameter, PositionParameterPK> {

    void deleteByIdPositionId(Long positionId);
    void deleteByIdParameterId(Long parameterId);
}

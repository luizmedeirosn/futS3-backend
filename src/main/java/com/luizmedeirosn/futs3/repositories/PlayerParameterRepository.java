package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PlayerParameter;
import com.luizmedeirosn.futs3.entities.pks.PlayerParameterPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerParameterRepository extends JpaRepository<PlayerParameter, PlayerParameterPK> {

    void deleteByIdPlayerId(Long playerId);
    void deleteByIdParameterId(Long parameterId);
}

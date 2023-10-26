package com.luizmedeirosn.futs3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmedeirosn.futs3.entities.PositionParameter;

public interface PositionParameterRepository extends JpaRepository<PositionParameter, Long> {

    // @Query(
    //     nativeQuery = true,
    //     value = """

    //             """
    // ) List<PositionParametersProjection> findAllPositionsParameters();

}

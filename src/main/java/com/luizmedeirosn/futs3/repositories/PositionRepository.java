package com.luizmedeirosn.futs3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmedeirosn.futs3.entities.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}

package com.luizmedeirosn.futs3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmedeirosn.futs3.entities.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}

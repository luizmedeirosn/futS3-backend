package com.luizmedeirosn.futs3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luizmedeirosn.futs3.entities.PlayerPicture;

@Repository
public interface PlayerPictureRepository extends JpaRepository<PlayerPicture, Long> {
}

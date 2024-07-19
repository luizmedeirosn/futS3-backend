package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerPictureRepository extends JpaRepository<PlayerPicture, Long> {}

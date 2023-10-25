package com.luizmedeirosn.futs3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.PlayerProjection;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query (
        nativeQuery = true,
        value = """
            SELECT 
                play.id AS playerId,
                play.name AS playerName,
                pos.id AS positionId,
                pos.name AS positionName,
                playpic.content as playerPicture
            FROM 
                tb_player AS play
                INNER JOIN tb_position AS pos
                    ON play.position_id = pos.id
                INNER JOIN tb_player_picture AS playpic
                    ON play.id = playpic.player_id
            ORDER BY pos.id;
        """
    ) List<PlayerProjection> findAllOptimized();

}

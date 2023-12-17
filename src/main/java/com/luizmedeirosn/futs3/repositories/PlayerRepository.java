package com.luizmedeirosn.futs3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query(nativeQuery = true, value = """
                SELECT
                    PLAY.id AS playerId,
                    PLAY.name AS playerName,
                    POS.id AS positionId,
                    POS.name AS positionName,
                    PLAYPIC.content as playerProfilePicture
                FROM
                    tb_player AS PLAY
                    INNER JOIN tb_position AS POS
                        ON PLAY.position_id = POS.id
                    LEFT JOIN tb_player_picture AS PLAYPIC
                        ON PLAY.id = PLAYPIC.player_id
                ORDER BY PLAY.name DESC;
            """)
    List<PlayerProjection> findAllOptimized();

}

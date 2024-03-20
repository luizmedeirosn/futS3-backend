package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query(nativeQuery = true, value = """
                    SELECT
                        PLAY.id AS playerId,
                        PLAY.name AS playerName,
                        POS.id AS positionId,
                        POS.name AS positionName,
                        POS.description AS positionDescription,
                        PLAYPIC.content AS playerPicture
                    FROM
                        tb_player AS PLAY
                        INNER JOIN tb_position AS POS
                            ON PLAY.position_id = POS.id
                        LEFT JOIN tb_player_picture AS PLAYPIC
                            ON PLAY.id = PLAYPIC.player_id
                    ORDER BY PLAY.name ASC;
            """)
    List<PlayerProjection> findAllOptimized();

    @Query(nativeQuery = true, value = """
                SELECT
                    play.id AS playerId,
                    play.name AS playerName,
                    play.age AS playerAge,
                    play.height AS playerHeight,
                    play.team AS playerTeam,
                    playpic.content AS playerPicture,
                    pos.id AS positionId,
                    pos.name AS positionName,
                    pos.description AS positionDescription,
                    param.id AS parameterId,
                    param.name AS parameterName,
                    playparam.score AS playerScore
                FROM
                    tb_player AS play
                        INNER JOIN tb_position AS pos ON play.position_id = pos.id
                        LEFT JOIN tb_player_picture AS playpic ON play.id = playpic.player_id
                        LEFT JOIN tb_player_parameter AS playparam ON play.id = playparam.player_id
                        LEFT JOIN tb_parameter AS param ON playparam.parameter_id = param.id
                WHERE play.id = :id ;
            """)
    Optional<List<PlayerProjection>> findByIdOptimized(@Param("id") Long id);


    @Modifying
    @Query(nativeQuery = true, value = """
                    delete from tb_player_parameter where player_id = :id ;
                    delete from tb_player_picture where player_id = :id ;
                    delete from tb_player where id = :id ;
            """)
    void deleteByIdWithParameters(@Param("id") Long id);
}

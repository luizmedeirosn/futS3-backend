package com.luizmedeirosn.futs3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luizmedeirosn.futs3.entities.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Modifying
    @Query(nativeQuery = true, value = """
                delete from tb_position_parameter where position_id = :id\\;
                delete from tb_position where id = :id\\;
            """)
    void deleteById(Long id);

}

package com.luizmedeirosn.futs3.repositories;

import com.luizmedeirosn.futs3.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Query(nativeQuery = true, value = """
                DELETE FROM tb_position_parameter WHERE position_id = :id ;
                DELETE FROM tb_position WHERE id = :id ;
            """)
    void deleteById(Long id);

}

package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;

import org.hibernate.annotations.Check;

import com.luizmedeirosn.futs3.entities.pks.PlayerParameterPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_player_parameter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PlayerParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PlayerParameterPK id;

    @Column(nullable = false)
    @Check(constraints = "score > 0 AND score <= 100")
    private Integer score;

    public PlayerParameter(Player player, Parameter parameter, Integer score) {
        id = new PlayerParameterPK();
        id.setPlayer(player);
        id.setParameter(parameter);
        this.score = score;
    }

}

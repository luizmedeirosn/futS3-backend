package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;

import org.hibernate.annotations.Check;

import com.luizmedeirosn.futs3.entities.pks.PlayerParameterPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_player_parameter")
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    public Player getPlayer() {
        return id.getPlayer();
    }

    public void setPlayer(Player player) {
        id.setPlayer(player);
    }

    public Parameter getParameter() {
        return id.getParameter();
    }

    public void setParameter(Parameter parameter) {
        id.setParameter(parameter);
    }

}

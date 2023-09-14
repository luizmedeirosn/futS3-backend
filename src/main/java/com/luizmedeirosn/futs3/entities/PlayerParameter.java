package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;

import org.hibernate.annotations.Check;

import com.luizmedeirosn.futs3.entities.pks.PlayerParameterPK;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_player_parameter")
public class PlayerParameter implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PlayerParameterPK id;

    @Nonnull
    @Column(nullable = false)
    @Check(constraints = "score > 0 AND score <= 100")
    private Integer score;



    public PlayerParameter() {
    }

    public PlayerParameter(Player player, Parameter parameter, Integer score) {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayerParameter other = (PlayerParameter) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}

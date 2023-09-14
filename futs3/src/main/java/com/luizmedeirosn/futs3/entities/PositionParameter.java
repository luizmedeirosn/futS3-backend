package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;

import org.hibernate.annotations.Check;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizmedeirosn.futs3.entities.pks.PositionParameterPK;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_position_parameter")
public class PositionParameter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private PositionParameterPK id = new PositionParameterPK();

    @Nonnull
    @Column(nullable = false)
    @Check(constraints = "weight > 0 AND weight <= 100")
    private Integer weight;
    
    public PositionParameter() {
    }

    public PositionParameter(Position position, Parameter parameter, Integer weight) {
        id.setPosition(position);
        id.setParameter(parameter);
        this.weight = weight;
    }

    public Position getPosition() {
        return id.getPosition();
    }

    public void setPosition(Position position) {
        id.setPosition(position);
    }

    @JsonIgnore
    public Parameter getParameter() {
        return id.getParameter();
    }

    public void setParameter(Parameter parameter) {
        id.setParameter(parameter);
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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
        PositionParameter other = (PositionParameter) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}

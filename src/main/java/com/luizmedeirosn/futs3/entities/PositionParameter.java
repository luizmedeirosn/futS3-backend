package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;

import org.hibernate.annotations.Check;

import com.luizmedeirosn.futs3.entities.pks.PositionParameterPK;

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
@Table(name = "tb_position_parameter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PositionParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PositionParameterPK id = new PositionParameterPK();

    @Column(nullable = false)
    @Check(constraints = "weight > 0 AND weight <= 100")
    private Integer weight;

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

    public Parameter getParameter() {
        return id.getParameter();
    }

    public void setParameter(Parameter parameter) {
        id.setParameter(parameter);
    }

}

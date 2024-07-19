package com.luizmedeirosn.futs3.entities;

import com.luizmedeirosn.futs3.entities.pks.PositionParameterPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "tb_position_parameter")
public class PositionParameter implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @EmbeddedId private final PositionParameterPK id = new PositionParameterPK();

  @Column(nullable = false)
  @Check(constraints = "weight > 0 AND weight <= 100")
  private Integer weight;

  public PositionParameter() {}

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

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PositionParameter that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

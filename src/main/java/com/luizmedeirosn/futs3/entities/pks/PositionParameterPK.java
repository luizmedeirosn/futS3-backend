package com.luizmedeirosn.futs3.entities.pks;

import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.entities.Position;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PositionParameterPK implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @ManyToOne(optional = false)
  @JoinColumn(name = "position_id")
  private Position position;

  @ManyToOne(optional = false)
  @JoinColumn(name = "parameter_id")
  private Parameter parameter;

  public PositionParameterPK() {}

  public PositionParameterPK(Position position, Parameter parameter) {
    this.position = position;
    this.parameter = parameter;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Parameter getParameter() {
    return parameter;
  }

  public void setParameter(Parameter parameter) {
    this.parameter = parameter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PositionParameterPK that)) return false;
    return Objects.equals(position, that.position) && Objects.equals(parameter, that.parameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, parameter);
  }
}

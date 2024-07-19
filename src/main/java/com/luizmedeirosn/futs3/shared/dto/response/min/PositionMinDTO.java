package com.luizmedeirosn.futs3.shared.dto.response.min;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Position;
import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"id", "name", "position"})
public record PositionMinDTO(Long id, String name, String description) implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  public PositionMinDTO(Position entity) {
    this(entity.getId(), entity.getName(), entity.getDescription());
  }

  public PositionMinDTO(Long id, String name) {
    this(id, name, null);
  }
}

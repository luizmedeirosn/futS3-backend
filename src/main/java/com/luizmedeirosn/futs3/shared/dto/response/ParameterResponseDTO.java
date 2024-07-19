package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Parameter;
import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"id", "name", "position"})
public record ParameterResponseDTO(Long id, String name, String description) implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  public ParameterResponseDTO(Parameter parameter) {
    this(parameter.getId(), parameter.getName(), parameter.getDescription());
  }
}

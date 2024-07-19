package com.luizmedeirosn.futs3.shared.dto.request;

import com.luizmedeirosn.futs3.entities.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

public record ParameterRequestDTO(
    @NotNull @Size(min = 3, max = 50, message = "Enter a name between 3 and 50 characters") String name,
    @Size(max = 2000, message = "Enter a description with a maximum of 2000 characters") String description)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  public ParameterRequestDTO(Parameter parameter) {
    this(parameter.getName(), parameter.getDescription());
  }
}

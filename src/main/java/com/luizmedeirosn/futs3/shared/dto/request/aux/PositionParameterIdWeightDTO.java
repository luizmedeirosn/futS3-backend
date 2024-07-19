package com.luizmedeirosn.futs3.shared.dto.request.aux;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

public record PositionParameterIdWeightDTO(@NotNull Long id, @NotNull @Min(1) @Max(100) Integer weight)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}

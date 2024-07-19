package com.luizmedeirosn.futs3.shared.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record GameModeRequestDTO(
    @NotNull @Size(min = 3, max = 50, message = "Enter a formation name between 3 and 20 characters")
        String formationName,
    @Size(max = 2000, message = "Enter a description with a maximum of 2000 characters") String description,
    @NotNull @Valid List<Long> positions)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}

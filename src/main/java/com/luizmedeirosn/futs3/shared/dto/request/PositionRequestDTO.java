package com.luizmedeirosn.futs3.shared.dto.request;

import com.luizmedeirosn.futs3.shared.dto.request.aux.PositionParameterIdWeightDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public record PositionRequestDTO(

        @NotNull @Size(min = 3, max = 50, message = "Enter a name between 3 and 50 characters") String name,
        @Size(max = 2000, message = "Enter a description with a maximum of 2000 characters") String description,
        @NotNull @Valid List<PositionParameterIdWeightDTO> parameters

) implements Serializable {
    private static final long serialVersionUID = 1L;
}

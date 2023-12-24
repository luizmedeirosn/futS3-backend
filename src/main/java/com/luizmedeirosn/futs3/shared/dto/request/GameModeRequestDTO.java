package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;
import java.util.List;

import com.luizmedeirosn.futs3.shared.dto.request.aux.PositionParametersDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GameModeRequestDTO(

        @NotNull @Size(min = 3, max = 20, message = "Enter a title between 3 and 20 characters") String formationName,
        @NotNull @Size(min = 3, max = 2000, message = "Enter a title between 3 and 2000 characters") String description,
        @NotNull @Valid List<PositionParametersDTO> positionsParameters

) implements Serializable {

    private static final long serialVersionUID = 1L;

}

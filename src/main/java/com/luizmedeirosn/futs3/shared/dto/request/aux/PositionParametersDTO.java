package com.luizmedeirosn.futs3.shared.dto.request.aux;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public record PositionParametersDTO(

        @NotNull Long positionId,
        @NotNull @Valid List<ParameterWeightDTO> parameters

) implements Serializable {
    private static final long serialVersionUID = 1L;
}

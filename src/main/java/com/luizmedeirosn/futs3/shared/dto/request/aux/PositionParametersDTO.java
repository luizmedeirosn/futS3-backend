package com.luizmedeirosn.futs3.shared.dto.request.aux;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PositionParametersDTO(

        @NotNull Long positionId,
        @NotNull @Valid List<ParameterWeightDTO> parameters

) implements Serializable {

    private static final long serialVersionUID = 1L;

}

package com.luizmedeirosn.futs3.shared.dto.request.aux;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ParameterWeightDTO(

        @NotNull Long parameterId,
        @Min(1) @Max(100) Integer weight

) implements Serializable {

    private static final long serialVersionUID = 1L;

}

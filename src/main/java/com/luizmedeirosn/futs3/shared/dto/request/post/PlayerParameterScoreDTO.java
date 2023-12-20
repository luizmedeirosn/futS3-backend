package com.luizmedeirosn.futs3.shared.dto.request.post;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PlayerParameterScoreDTO(

        @NotNull Long id,
        @NotNull @Min(1) @Max(100) Integer score

) implements Serializable {

    private static final long serialVersionUID = 1L;

}

package com.luizmedeirosn.futs3.shared.dto.request.aux;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.PlayerParameter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PlayerParameterIdScoreDTO(

        @NotNull Long id,
        @NotNull @Min(1) @Max(100) Integer score

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlayerParameterIdScoreDTO(PlayerParameter playerParameter) {
        this(playerParameter.getId().getParameter().getId(), playerParameter.getScore());
    }

}

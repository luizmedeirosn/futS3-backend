package com.luizmedeirosn.futs3.shared.dto.response.aux;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;

import java.io.Serializable;

@JsonPropertyOrder({"id", "name", "score"})
public record PlayerParameterDataDTO(

        Long id,
        String name,
        Integer score
) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlayerParameterDataDTO(PlayerProjection playerData) {
        this(
                playerData.getParameterId(),
                playerData.getParameterName(),
                playerData.getPlayerScore()
        );
    }
}

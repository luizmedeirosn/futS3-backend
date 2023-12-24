package com.luizmedeirosn.futs3.shared.dto.response.min;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.GameMode;

@JsonPropertyOrder({ "id", "formationName", "description" })
public record GameModeMinResponseDTO(

        Long id,
        String formationName,
        String description

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public GameModeMinResponseDTO(GameMode gameMode) {
        this(
                gameMode.getId(),
                gameMode.getFormationName(),
                gameMode.getDescription());
    }

}
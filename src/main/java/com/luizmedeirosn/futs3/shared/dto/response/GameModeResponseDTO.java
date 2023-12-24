package com.luizmedeirosn.futs3.shared.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.projections.gamemode.GameModeProjection;

@JsonPropertyOrder({ "id", "formationName", "description", "fields" })
public record GameModeResponseDTO(

        Long id,
        String formationName,
        String description,
        List<GameModeProjection> fields

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public GameModeResponseDTO(GameMode gameMode, List<GameModeProjection> fields) {
        this(
                gameMode.getId(),
                gameMode.getFormationName(),
                gameMode.getDescription(),
                fields);
    }

}

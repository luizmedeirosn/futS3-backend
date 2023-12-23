package com.luizmedeirosn.futs3.shared.dto.response.min;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.GameMode;

@JsonPropertyOrder({ "id", "formationName", "description" })
public class GameModeMinResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String formationName;
    private String description;

    public GameModeMinResponseDTO() {
    }

    public GameModeMinResponseDTO(GameMode gameMode) {
        id = gameMode.getId();
        formationName = gameMode.getFormationName();
        description = gameMode.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getFormationName() {
        return formationName;
    }

    public String getDescription() {
        return description;
    }

}
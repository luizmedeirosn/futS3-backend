package com.luizmedeirosn.futs3.dto;

import com.luizmedeirosn.futs3.entities.GameMode;

public class GameModeMinDTO {
    
    private String formationName;
    private String description;
    
    public GameModeMinDTO(GameMode obj) {
        formationName = obj.getFormationName();
        description = obj.getDescription();
    }

    public String getFormationName() {
        return formationName;
    }

    public String getDescription() {
        return description;
    }

}

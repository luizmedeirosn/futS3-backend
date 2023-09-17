package com.luizmedeirosn.futs3.dto;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.GameMode;

public class GameModeMinDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String formationName;
    private String description;
    
    public GameModeMinDTO() {
    }

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

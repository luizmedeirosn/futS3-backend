package com.luizmedeirosn.futs3.dto;

import java.io.Serializable;

public class GameModeMinDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String formationName;
    private String description;
    
    public GameModeMinDTO() {
    }

    public String getFormationName() {
        return formationName;
    }

    public String getDescription() {
        return description;
    }

}

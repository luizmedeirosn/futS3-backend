package com.luizmedeirosn.futs3.dto.output.min;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.GameMode;

public class GameModeMinDTO implements Serializable, Comparable<GameModeMinDTO>{
    
    private static final long serialVersionUID = 1L;
        
    private Long id;
    private String formationName;
    private String description;
    
    public GameModeMinDTO() {
    }

    public GameModeMinDTO(GameMode gameMode) {
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

    @Override
    public int compareTo(GameModeMinDTO other) {
        return id.compareTo(other.getId());
    }

}
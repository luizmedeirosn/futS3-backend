package com.luizmedeirosn.futs3.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.projections.GameModeProjection;

@JsonPropertyOrder( { "id", "formationName", "description", "fields" } )
public class GameModeDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String formationName;
    private String description;
    private transient List<GameModeProjection> fields;
    
    public GameModeDTO() {
    }

    public GameModeDTO(GameMode gameMode, List<GameModeProjection> fields) {
        id = gameMode.getId();
        formationName = gameMode.getFormationName();
        description = gameMode.getDescription();
        this.fields = fields;
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

    public List<GameModeProjection> getFields() {
        return fields;
    }

}

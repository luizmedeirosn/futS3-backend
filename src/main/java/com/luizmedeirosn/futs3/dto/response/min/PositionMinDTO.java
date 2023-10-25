package com.luizmedeirosn.futs3.dto.response.min;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Position;

@JsonPropertyOrder( { "id", "name", "position" } )
public class PositionMinDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;

    public PositionMinDTO() {
    }

    public PositionMinDTO(Position entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
    }

     public PositionMinDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
}

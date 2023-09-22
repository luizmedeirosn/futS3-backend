package com.luizmedeirosn.futs3.dto.output;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Position;

@JsonPropertyOrder( { "id", "name", "position" } )
public class PositionDTO implements Serializable, Comparable<PositionDTO> {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;

    public PositionDTO() {
    }

    public PositionDTO(Position entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
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

    @Override
    public int compareTo(PositionDTO other) {
        return id.compareTo(other.getId());
    }
    
}

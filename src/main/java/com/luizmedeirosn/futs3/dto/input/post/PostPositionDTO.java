package com.luizmedeirosn.futs3.dto.input.post;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Position;

public class PostPositionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public PostPositionDTO() {
    }

    public PostPositionDTO(Position entity) {
        name = entity.getName();
        description = entity.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

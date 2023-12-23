package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Position;

public class PositionRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public PositionRequestDTO() {
    }

    public PositionRequestDTO(Position entity) {
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

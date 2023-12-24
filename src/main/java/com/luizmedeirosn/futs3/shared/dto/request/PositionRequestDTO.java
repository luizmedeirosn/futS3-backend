package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Position;

public record PositionRequestDTO(

        String name,
        String description

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PositionRequestDTO(Position entity) {
        this(entity.getName(), entity.getDescription());
    }

}

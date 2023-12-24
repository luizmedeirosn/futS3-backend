package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Position;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PositionRequestDTO(

        @NotNull @Size(min = 3, max = 20, message = "Enter a title between 3 and 20 characters") String name,
        @NotNull @Size(min = 3, max = 20, message = "Enter a title between 3 and 20 characters") String description

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PositionRequestDTO(Position entity) {
        this(entity.getName(), entity.getDescription());
    }

}

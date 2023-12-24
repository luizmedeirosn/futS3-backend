package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Parameter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParameterRequestDTO(

        @NotNull @Size(min = 3, max = 20, message = "Enter a title between 3 and 20 characters") String name,
        @NotNull @Size(min = 3, max = 2000, message = "Enter a title between 3 and 2000 characters") String description

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public ParameterRequestDTO(Parameter parameter) {
        this(parameter.getName(), parameter.getDescription());
    }

}
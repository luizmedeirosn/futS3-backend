package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Parameter;

public record ParameterRequestDTO(

        String name,
        String description

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public ParameterRequestDTO(Parameter parameter) {
        this(parameter.getName(), parameter.getDescription());
    }

}
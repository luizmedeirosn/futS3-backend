package com.luizmedeirosn.futs3.shared.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Parameter;

@JsonPropertyOrder({ "id", "name", "position" })
public record ParameterResponseDTO(

        Long id,
        String name,
        String description

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public ParameterResponseDTO(Parameter parameter) {
        this(
                parameter.getId(),
                parameter.getName(),
                parameter.getDescription());
    }

}
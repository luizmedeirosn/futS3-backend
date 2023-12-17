package com.luizmedeirosn.futs3.shared.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Parameter;

@JsonPropertyOrder({ "id", "name", "position" })
public class ParameterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;

    public ParameterDTO() {
    }

    public ParameterDTO(Parameter parameter) {
        id = parameter.getId();
        name = parameter.getName();
        description = parameter.getDescription();
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
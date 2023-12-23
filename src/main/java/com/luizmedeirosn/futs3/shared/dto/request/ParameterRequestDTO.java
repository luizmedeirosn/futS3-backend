package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Parameter;

public class ParameterRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public ParameterRequestDTO() {
    }

    public ParameterRequestDTO(Parameter parameter) {
        name = parameter.getName();
        description = parameter.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
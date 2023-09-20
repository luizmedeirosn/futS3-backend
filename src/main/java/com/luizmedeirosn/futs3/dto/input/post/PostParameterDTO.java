package com.luizmedeirosn.futs3.dto.input.post;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Parameter;

public class PostParameterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public PostParameterDTO() {
    }

    public PostParameterDTO(Parameter parameter) {
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
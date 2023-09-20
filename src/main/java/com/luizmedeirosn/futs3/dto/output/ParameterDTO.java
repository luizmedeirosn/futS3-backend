package com.luizmedeirosn.futs3.dto.output;

import java.io.Serializable;

import com.luizmedeirosn.futs3.entities.Parameter;

public class ParameterDTO implements Serializable, Comparable<ParameterDTO> {

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

    @Override
    public int compareTo(ParameterDTO other) {
        return id.compareTo(other.getId());
    }
    
}
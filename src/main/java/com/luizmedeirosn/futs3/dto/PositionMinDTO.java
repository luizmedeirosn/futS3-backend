package com.luizmedeirosn.futs3.dto;

import java.io.Serializable;

public class PositionMinDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    
    public PositionMinDTO() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

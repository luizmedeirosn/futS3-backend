package com.luizmedeirosn.futs3.dto.request.update;

import java.io.Serializable;

public class UpdateParameterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

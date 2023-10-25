package com.luizmedeirosn.futs3.dto.request.update;

import java.io.Serializable;

public class UpdateGameModeDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String formationName;
    private String description;
    
    public String getFormationName() {
        return formationName;
    }

    public String getDescription() {
        return description;
    }

}
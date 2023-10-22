package com.luizmedeirosn.futs3.dto.request.post;

import java.io.Serializable;
import java.util.List;

public class PostGameModeDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String formationName;
    private String description;
    private List<PositionParametersDTO> positionsParameters;
    
    public String getFormationName() {
        return formationName;
    }

    public String getDescription() {
        return description;
    }

    public List<PositionParametersDTO> getPositionsParameters() {
        return positionsParameters;
    }

}

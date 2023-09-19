package com.luizmedeirosn.futs3.dto.input;

import java.io.Serializable;
import java.util.List;

public class PlayerInputDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private Long positionId;
    private List<ParameterPlayerScoreDTO> parameters;
    
    public PlayerInputDTO() {
    }

    public PlayerInputDTO(String name, Long positionId, List<ParameterPlayerScoreDTO> parameters) {
        this.name = name;
        this.positionId = positionId;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public Long getPositionId() {
        return positionId;
    }

    public List<ParameterPlayerScoreDTO> getParameters() {
        return parameters;
    }
    
}

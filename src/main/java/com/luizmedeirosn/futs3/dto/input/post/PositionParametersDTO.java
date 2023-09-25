package com.luizmedeirosn.futs3.dto.input.post;

import java.io.Serializable;
import java.util.List;

public class PositionParametersDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long positionId;
    private List<ParameterWeightDTO> parameters;
    
    public PositionParametersDTO() {
    }

    public PositionParametersDTO(Long positionId, List<ParameterWeightDTO> parameters) {
        this.positionId = positionId;
        this.parameters = parameters;
    }

    public Long getPositionId() {
        return positionId;
    }

    public List<ParameterWeightDTO> getParameters() {
        return parameters;
    }

}

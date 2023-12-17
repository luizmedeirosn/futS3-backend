package com.luizmedeirosn.futs3.shared.dto.request.post;

import java.io.Serializable;
import java.util.List;

public class PostPlayerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Long positionId;
    private List<ParameterPlayerScoreDTO> parameters;

    public PostPlayerDTO() {
    }

    public PostPlayerDTO(String name, Long positionId, List<ParameterPlayerScoreDTO> parameters) {
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

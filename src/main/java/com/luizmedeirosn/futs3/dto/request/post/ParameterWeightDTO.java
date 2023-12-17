package com.luizmedeirosn.futs3.dto.request.post;

import java.io.Serializable;

public class ParameterWeightDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long parameterId;
    private Integer weight;

    public ParameterWeightDTO() {
    }

    public ParameterWeightDTO(Long parameterId, Integer weight) {
        this.parameterId = parameterId;
        this.weight = weight;
    }

    public Long getParameterId() {
        return parameterId;
    }

    public Integer getWeight() {
        return weight;
    }

}

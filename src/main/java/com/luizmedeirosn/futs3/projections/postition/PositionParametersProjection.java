package com.luizmedeirosn.futs3.projections.postition;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "parameterId", "parameterName", "parameterWeight" })
public interface PositionParametersProjection {

    Long getParameterId();

    String getParameterName();

    Integer getParameterWeight();

}

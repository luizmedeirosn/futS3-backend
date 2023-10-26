package com.luizmedeirosn.futs3.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "parameterId", "parameterName", "parameterWeight" } )
public interface PositionParametersProjection {
    
    Long getParameterId();
    String getParameterName();
    Long getParameterWeight();

}

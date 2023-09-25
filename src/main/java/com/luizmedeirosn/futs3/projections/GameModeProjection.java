package com.luizmedeirosn.futs3.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "positionId", "positionName", "parameterId", "parameterName", "parameterWeight" } )
public interface GameModeProjection {
    
    Long getPositionId();
    String getPositionName();
    Long getParameterId();
    String getParameterName();
    Integer getParameterWeight();

}

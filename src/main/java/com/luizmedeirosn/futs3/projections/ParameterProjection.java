package com.luizmedeirosn.futs3.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "id", "playerScore", "name", "description" } )
public interface ParameterProjection {
    
    Long getId();
    Integer getPlayerScore();
    String getName();
    String getDescription();

}

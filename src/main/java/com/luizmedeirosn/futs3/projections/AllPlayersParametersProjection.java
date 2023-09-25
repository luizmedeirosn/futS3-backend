package com.luizmedeirosn.futs3.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "playerId", "playerName", "parameterId", "parameterName", "playerScore" } )
public interface AllPlayersParametersProjection {
    
    Long getPlayerId();
    String getPlayerName();
    Long getParameterId();
    String getParameterName();
    Integer getPlayerScore();

}

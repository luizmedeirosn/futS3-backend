package com.luizmedeirosn.futs3.projections.player;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "playerId", "name", "score" })
public interface PlayerParameterProjection {

    Long getId();
    String getName();
    Integer getScore();

}

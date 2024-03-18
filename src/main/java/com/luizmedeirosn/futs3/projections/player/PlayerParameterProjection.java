package com.luizmedeirosn.futs3.projections.player;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "score" })
public interface PlayerParameterProjection {

    Long getId();
    String getName();
    Integer getScore();

}

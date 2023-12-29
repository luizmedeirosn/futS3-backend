package com.luizmedeirosn.futs3.projections.postition;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "weight" })
public interface PositionParametersProjection {

    Long getId();

    String getName();

    Integer getWeight();

}

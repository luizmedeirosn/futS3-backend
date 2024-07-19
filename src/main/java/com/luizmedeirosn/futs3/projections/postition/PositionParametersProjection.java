package com.luizmedeirosn.futs3.projections.postition;

public interface PositionParametersProjection {
  Long getId();

  String getName();

  String getDescription();

  Long getParameterId();

  String getParameterName();

  Integer getPositionWeight();
}

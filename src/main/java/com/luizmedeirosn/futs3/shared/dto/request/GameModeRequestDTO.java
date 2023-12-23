package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;
import java.util.List;

import com.luizmedeirosn.futs3.shared.dto.request.aux.PositionParametersDTO;

public class GameModeRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String formationName;
    private String description;
    private List<PositionParametersDTO> positionsParameters;

    public String getFormationName() {
        return formationName;
    }

    public String getDescription() {
        return description;
    }

    public List<PositionParametersDTO> getPositionsParameters() {
        return positionsParameters;
    }

}

package com.luizmedeirosn.futs3.shared.dto.request.aux;

import java.io.Serializable;

public record ParameterWeightDTO(

        Long parameterId,
        Integer weight

) implements Serializable {

    private static final long serialVersionUID = 1L;

}

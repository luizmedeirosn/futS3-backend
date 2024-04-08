package com.luizmedeirosn.futs3.shared.dto.response.aux;

import java.io.Serializable;

public record PositionParametersDataDTO(

        Long id,
        String name,
        Integer weight

) implements Serializable {
    private static final long serialVersionUID = 1L;
}

package com.luizmedeirosn.futs3.shared.dto.response;

import java.io.Serializable;

public record TokenResponseDTO(

        String accessToken,
        String refreshToken

) implements Serializable {
    private static final long serialVersionUID = 1L;
}
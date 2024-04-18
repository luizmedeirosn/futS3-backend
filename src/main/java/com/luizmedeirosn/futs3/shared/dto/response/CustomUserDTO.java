package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.CustomUser;

import java.io.Serializable;

@JsonPropertyOrder({ "id", "username", "email" })
public record CustomUserDTO(

        Long id,
        String username,
        String email

) implements Serializable {
    private static final long serialVersionUID = 1L;

    public CustomUserDTO(CustomUser user) {
        this(user.getId(), user.getUsername(), user.getEmail());
    }
}

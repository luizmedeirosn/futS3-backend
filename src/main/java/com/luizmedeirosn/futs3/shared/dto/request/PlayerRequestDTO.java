package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlayerRequestDTO(

        @NotNull @NotBlank @Size(min = 3, max = 30, message = "Enter a title between 3 and 50 characters") String name,
        @NotNull @NotBlank @Size(min = 3, max = 30, message = "Enter a title between 3 and 50 characters") String team,
        @Min(1) @Max(150) Integer age,
        @Min(65) @Max(250) Integer height,
        @NotNull Long positionId,
        MultipartFile playerPicture,
        @NotNull String parameters

) implements Serializable {
    private static final long serialVersionUID = 1L;
}

package com.luizmedeirosn.futs3.security;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luizmedeirosn.futs3.shared.dto.request.SigninRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.SignupRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.SignupResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.TokenResponseDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Valid SignupRequestDTO signup) {
        SignupResponseDTO response = authService.signup(signup);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/users/" + response.user().getId())
                .build()
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/auth/signin")
    public TokenResponseDTO signin(@RequestBody @Valid SigninRequestDTO signin) {
        return authService.signin(signin);
    }

    @PutMapping("/auth/refresh-token")
    public TokenResponseDTO refreshToken(
            @RequestHeader("Authorization") @NotNull @NotBlank String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

}

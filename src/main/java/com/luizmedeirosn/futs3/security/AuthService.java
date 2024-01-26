package com.luizmedeirosn.futs3.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizmedeirosn.futs3.entities.CustomUser;
import com.luizmedeirosn.futs3.repositories.CustomUserRepository;
import com.luizmedeirosn.futs3.security.jwt.JwtService;
import com.luizmedeirosn.futs3.shared.dto.request.SigninRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.request.SignupRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.SignupResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.TokenResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomUserRepository customUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public SignupResponseDTO signup(SignupRequestDTO signup) {
        var user = customUserRepository.save(new CustomUser(signup));
        return new SignupResponseDTO(user, jwtService.generateToken(user.getUsername()));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public TokenResponseDTO signin(SigninRequestDTO signinRequestDTO) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequestDTO.username(), signinRequestDTO.password()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(signinRequestDTO.username());
        } else {
            throw new BadCredentialsException("Authentication Failure");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public TokenResponseDTO refreshToken(String refreshToken) {
        return jwtService.refreshToken(refreshToken);
    }

}

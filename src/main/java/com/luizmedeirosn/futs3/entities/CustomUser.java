package com.luizmedeirosn.futs3.entities;

import com.luizmedeirosn.futs3.shared.dto.request.SignupRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private String roles;

    public CustomUser(SignupRequestDTO signupRequestDTO) {
        username = signupRequestDTO.username();
        email = signupRequestDTO.email();
        password = signupRequestDTO.password();
        roles = signupRequestDTO.roles();
    }

}

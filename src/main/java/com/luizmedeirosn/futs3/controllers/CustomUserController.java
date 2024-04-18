package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.entities.CustomUser;
import com.luizmedeirosn.futs3.services.CustomUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class CustomUserController {

    private final CustomUserService customUserService;

    public CustomUserController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<CustomUser>> findAll() {
        return ResponseEntity.ok().body(customUserService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomUser> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(customUserService.findById(id));
    }
}

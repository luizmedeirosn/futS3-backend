package com.luizmedeirosn.futs3.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizmedeirosn.futs3.entities.CustomUser;
import com.luizmedeirosn.futs3.services.CustomUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class CustomUserController {

    private final CustomUserService customUserService;

    @GetMapping
    public ResponseEntity<List<CustomUser>> findAll() {
        return ResponseEntity.ok().body(customUserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomUser> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(customUserService.findById(id));
    }

}

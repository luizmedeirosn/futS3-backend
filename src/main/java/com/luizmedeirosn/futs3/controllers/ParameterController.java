package com.luizmedeirosn.futs3.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luizmedeirosn.futs3.services.ParameterSerivce;
import com.luizmedeirosn.futs3.shared.dto.request.ParameterRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.ParameterResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/parameters")
@RequiredArgsConstructor
public class ParameterController {

    private final ParameterSerivce parameterSerivce;

    @GetMapping
    public ResponseEntity<List<ParameterResponseDTO>> findAll() {
        return ResponseEntity.ok().body(parameterSerivce.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ParameterResponseDTO> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(parameterSerivce.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParameterResponseDTO> save(@RequestBody @Valid ParameterRequestDTO parameterRequestDTO) {
        ParameterResponseDTO parameterDTO = parameterSerivce.save(parameterRequestDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(parameterDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(parameterDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ParameterResponseDTO> updateById(@PathVariable @NonNull Long id,
            @RequestBody @Valid ParameterRequestDTO parameterRequestDTO) {
        return ResponseEntity.ok().body(parameterSerivce.update(id, parameterRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        parameterSerivce.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.services.ParameterSerivce;
import com.luizmedeirosn.futs3.shared.dto.request.ParameterRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.ParameterResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/parameters")
public class ParameterController {

    private final ParameterSerivce parameterSerivce;

    public ParameterController(ParameterSerivce parameterSerivce) {
        this.parameterSerivce = parameterSerivce;
    }

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
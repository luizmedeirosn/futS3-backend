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

import com.luizmedeirosn.futs3.services.PositionService;
import com.luizmedeirosn.futs3.shared.dto.request.PositionRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PositionResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<List<PositionMinDTO>> findAll() {
        return ResponseEntity.ok().body(positionService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PositionMinDTO> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(positionService.findById(id));
    }

    @GetMapping(value = "/parameters")
    public ResponseEntity<List<PositionResponseDTO>> findAllWithParameters() {
        return ResponseEntity.ok().body(positionService.findAllWithParameters());
    }

    @GetMapping(value = "/{id}/parameters")
    public ResponseEntity<PositionResponseDTO> findPositionParametersById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(positionService.findByIdPositionParameters(id));
    }

    @PostMapping
    public ResponseEntity<PositionMinDTO> save(@RequestBody @Valid PositionRequestDTO postPositionDTO) {
        PositionMinDTO positionDTO = positionService.save(postPositionDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(positionDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(positionDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PositionMinDTO> updateById(@PathVariable @NonNull Long id,
            @RequestBody @Valid PositionRequestDTO positionRequestDTO) {
        return ResponseEntity.ok().body(positionService.update(id, positionRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        positionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

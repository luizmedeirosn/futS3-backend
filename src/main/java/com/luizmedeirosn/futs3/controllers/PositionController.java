package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.services.PositionService;
import com.luizmedeirosn.futs3.shared.dto.request.PositionRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PositionResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<List<PositionMinDTO>> findAll() {
        return ResponseEntity.ok().body(positionService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PositionResponseDTO> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(positionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PositionResponseDTO> save(@RequestBody @Valid PositionRequestDTO postPositionDTO) {
        PositionResponseDTO positionDTO = positionService.save(postPositionDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(positionDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(positionDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PositionMinDTO> updateById(
            @PathVariable @NonNull Long id,
            @RequestBody @Valid PositionRequestDTO positionRequestDTO
    ) {
        return ResponseEntity.ok().body(positionService.update(id, positionRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        positionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

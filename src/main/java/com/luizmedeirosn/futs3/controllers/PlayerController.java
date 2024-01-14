package com.luizmedeirosn.futs3.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luizmedeirosn.futs3.projections.player.AllPlayersParametersProjection;
import com.luizmedeirosn.futs3.services.PlayerService;
import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PlayerMinResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerMinResponseDTO>> findAll() {
        return ResponseEntity.ok().body(playerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerMinResponseDTO> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(playerService.findById(id));
    }

    @GetMapping("/parameters")
    public ResponseEntity<List<AllPlayersParametersProjection>> findAllWithParameters() {
        return ResponseEntity.ok().body(playerService.findAllWithParameters());
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<PlayerResponseDTO> findFullById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(playerService.findFullById(id));
    }

    @PostMapping
    public ResponseEntity<PlayerResponseDTO> save(@ModelAttribute @Valid PlayerRequestDTO playerRequestDTO) {
        PlayerResponseDTO playerResponseDTO = playerService.save(playerRequestDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(playerResponseDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(playerResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PlayerMinResponseDTO> update(@PathVariable @NonNull Long id,
            @ModelAttribute @Valid PlayerRequestDTO playerRequestDTO) {
        System.out.println(playerRequestDTO);
        return ResponseEntity.ok().body(playerService.update(id, playerRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        playerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

package com.luizmedeirosn.futs3.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luizmedeirosn.futs3.projections.player.AllPlayersParametersProjection;
import com.luizmedeirosn.futs3.services.PlayerService;
import com.luizmedeirosn.futs3.shared.dto.request.post.PostPlayerDTO;
import com.luizmedeirosn.futs3.shared.dto.request.update.UpdatePlayerDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PlayerMinDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerMinDTO>> findAll() {
        return ResponseEntity.ok().body(playerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerMinDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(playerService.findById(id));
    }

    @GetMapping("/parameters")
    public ResponseEntity<List<AllPlayersParametersProjection>> findAllWithParameters() {
        return ResponseEntity.ok().body(playerService.findAllWithParameters());
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<PlayerDTO> findFullById(@PathVariable Long id) {
        return ResponseEntity.ok().body(playerService.findFullById(id));
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> save(@RequestBody PostPlayerDTO postPlayerDTO) {
        PlayerDTO playerDTO = playerService.save(postPlayerDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(playerDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(playerDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PlayerMinDTO> update(@PathVariable Long id, @RequestBody UpdatePlayerDTO updatePlayerDTO) {
        return ResponseEntity.ok().body(playerService.update(id, updatePlayerDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        playerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

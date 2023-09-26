package com.luizmedeirosn.futs3.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.luizmedeirosn.futs3.dto.input.post.PostPlayerDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdatePlayerDTO;
import com.luizmedeirosn.futs3.dto.output.PlayerDTO;
import com.luizmedeirosn.futs3.dto.output.min.PlayerMinDTO;
import com.luizmedeirosn.futs3.projections.AllPlayersParametersProjection;
import com.luizmedeirosn.futs3.projections.PlayerProjection;
import com.luizmedeirosn.futs3.services.PlayerService;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {
    
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerProjection>> findAll() {
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

    @GetMapping("/{id}/parameters")
    public ResponseEntity<PlayerDTO> findByIdWithParameters(@PathVariable Long id) {
        return ResponseEntity.ok().body(playerService.findByIdWithParameters(id));
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> save(@RequestBody PostPlayerDTO postPlayerDTO) {
        PlayerDTO playerDTO = playerService.save(postPlayerDTO);
        URI uri = 
            ServletUriComponentsBuilder
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

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
        PlayerMinDTO playerMinDTO = playerService.findById(id);
        ResponseEntity<PlayerMinDTO> response = ResponseEntity.ok().body(playerMinDTO);
        return response;
    }

    @GetMapping("/parameters")
    public ResponseEntity<List<AllPlayersParametersProjection>> findAllWithParameters() {
        return ResponseEntity.ok().body(playerService.findAllWithParameters());
    }

    @GetMapping("/{id}/parameters")
    public ResponseEntity<PlayerDTO> findByIdWithParameters(@PathVariable Long id) {
        PlayerDTO playerDTO = playerService.findByIdWithParameters(id);
        ResponseEntity<PlayerDTO> response = ResponseEntity.ok().body(playerDTO);
        return response;
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
        ResponseEntity<PlayerDTO> response = ResponseEntity.created(uri).body(playerDTO);
        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PlayerMinDTO> update(@PathVariable Long id, @RequestBody UpdatePlayerDTO updatePlayerDTO) {
        PlayerMinDTO playerMinDTO = playerService.update(id, updatePlayerDTO);
        ResponseEntity<PlayerMinDTO> response = ResponseEntity.ok().body(playerMinDTO);
        return response;
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        playerService.deleteById(id);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return response;
    }

}

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

import com.luizmedeirosn.futs3.dto.request.post.PostGameModeDTO;
import com.luizmedeirosn.futs3.dto.request.update.UpdateGameModeDTO;
import com.luizmedeirosn.futs3.dto.response.GameModeDTO;
import com.luizmedeirosn.futs3.dto.response.min.GameModeMinDTO;
import com.luizmedeirosn.futs3.projections.gamemode.AllGameModesProjection;
import com.luizmedeirosn.futs3.services.GameModeService;

@RestController
@RequestMapping(value = "/gamemodes")
public class GameModeController {
    
    @Autowired
    private GameModeService gameModeService;

    @GetMapping
    public ResponseEntity<List<GameModeMinDTO>> findAll() {
        return ResponseEntity.ok().body(gameModeService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GameModeMinDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(gameModeService.findById(id));
    }

    @GetMapping(value = "/full")
    public ResponseEntity<List<AllGameModesProjection>> findAllFull() {
        return ResponseEntity.ok().body(gameModeService.findAllFull());
    }

    @GetMapping(value = "/{id}/full")
    public ResponseEntity<GameModeDTO> findFullById(@PathVariable Long id) {
        return ResponseEntity.ok().body(gameModeService.findFullById(id));
    }

    @PostMapping
    public ResponseEntity<GameModeDTO> save(@RequestBody PostGameModeDTO postGameModeDTO) {
        GameModeDTO gameModeDTO = gameModeService.save(postGameModeDTO);
        URI uri = 
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(gameModeDTO.getId())
            .toUri();
        return ResponseEntity.created(uri).body(gameModeDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GameModeMinDTO> updateById(@PathVariable Long id, @RequestBody UpdateGameModeDTO obj) {
        return ResponseEntity.ok().body(gameModeService.update(id, obj));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        gameModeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

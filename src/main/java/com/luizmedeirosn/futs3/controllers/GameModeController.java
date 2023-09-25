package com.luizmedeirosn.futs3.controllers;

import java.net.URI;
import java.util.Set;

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

import com.luizmedeirosn.futs3.dto.input.update.UpdateGameModeDTO;
import com.luizmedeirosn.futs3.dto.output.min.GameModeMinDTO;
import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.services.GameModeService;

@RestController
@RequestMapping(value = "/gamemodes")
public class GameModeController {
    
    @Autowired
    private GameModeService gameModeService;

    @GetMapping
    public ResponseEntity<Set<GameModeMinDTO>> findAll() {
        Set<GameModeMinDTO> gameModeMinDTOs = gameModeService.findAll();
        ResponseEntity<Set<GameModeMinDTO>> response = ResponseEntity.ok().body(gameModeMinDTOs);
        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GameModeMinDTO> findById(@PathVariable Long id) {
        GameModeMinDTO gameModeMinDTO = gameModeService.findById(id);
        ResponseEntity<GameModeMinDTO> response = ResponseEntity.ok().body(gameModeMinDTO);
        return response;
    }

    @PostMapping
    public ResponseEntity<GameMode> save(@RequestBody GameMode entity) {
        entity = gameModeService.save(entity);
        URI uri = 
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(entity.getId())
            .toUri();
        ResponseEntity<GameMode> response = ResponseEntity.created(uri).body(entity);
        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GameMode> updateById(@PathVariable Long id, @RequestBody UpdateGameModeDTO obj) {
        GameMode entity = gameModeService.update(id, obj);
        ResponseEntity<GameMode> response = ResponseEntity.ok().body(entity);
        return response;
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        gameModeService.deleteById(id);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return response;
    }

}

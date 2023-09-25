package com.luizmedeirosn.futs3.controllers;

import java.net.URI;
import java.util.List;
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

import com.luizmedeirosn.futs3.dto.input.post.PostGameModeDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdateGameModeDTO;
import com.luizmedeirosn.futs3.dto.output.GameModeDTO;
import com.luizmedeirosn.futs3.dto.output.min.GameModeMinDTO;
import com.luizmedeirosn.futs3.projections.AllGameModesProjection;
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

    @GetMapping(value = "/full")
    public ResponseEntity<List<AllGameModesProjection>> findAllFull() {
        List<AllGameModesProjection> fullGameModes = gameModeService.findAllFull();
        ResponseEntity<List<AllGameModesProjection>> response = ResponseEntity.ok().body(fullGameModes);
        return response;
    }

    @GetMapping(value = "/{id}/full")
    public ResponseEntity<GameModeDTO> findFullById(@PathVariable Long id) {
        GameModeDTO gameModeDTO = gameModeService.findFullById(id);
        ResponseEntity<GameModeDTO> response = ResponseEntity.ok().body(gameModeDTO);
        return response;
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
        ResponseEntity<GameModeDTO> response = ResponseEntity.created(uri).body(gameModeDTO);
        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GameModeMinDTO> updateById(@PathVariable Long id, @RequestBody UpdateGameModeDTO obj) {
        GameModeMinDTO gameModeMinDTO = gameModeService.update(id, obj);
        ResponseEntity<GameModeMinDTO> response = ResponseEntity.ok().body(gameModeMinDTO);
        return response;
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        gameModeService.deleteById(id);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return response;
    }

}

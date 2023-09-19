package com.luizmedeirosn.futs3.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizmedeirosn.futs3.dto.PlayerDTO;
import com.luizmedeirosn.futs3.dto.min.PlayerMinDTO;
import com.luizmedeirosn.futs3.services.PlayerService;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {
    
    @Autowired
    private PlayerService playerService;
    
    @GetMapping("/parameters")
    public ResponseEntity<Set<PlayerDTO>> findAllWithParameters() {
        Set<PlayerDTO> playersDTO = playerService.findAllWithParameters();
        ResponseEntity<Set<PlayerDTO>> response = ResponseEntity.ok().body(playersDTO);
        return response;
    }

    @GetMapping("/{id}/parameters")
    public ResponseEntity<PlayerDTO> findByIdWithParameters(@PathVariable Long id) {
        PlayerDTO playerDTO = playerService.findByIdWithParameters(id);
        ResponseEntity<PlayerDTO> response = ResponseEntity.ok().body(playerDTO);
        return response;
    }

    @GetMapping
    public ResponseEntity<Set<PlayerMinDTO>> findAll() {
        Set<PlayerMinDTO> playersMinDtos = playerService.findAll();
        ResponseEntity<Set<PlayerMinDTO>> response = ResponseEntity.ok().body(playersMinDtos);
        return response;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        playerService.deleteById(id);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return response;
    }

}

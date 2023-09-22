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

import com.luizmedeirosn.futs3.dto.input.post.PostPositionDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdatePositionDTO;
import com.luizmedeirosn.futs3.dto.output.PositionDTO;
import com.luizmedeirosn.futs3.services.PositionService;

@RestController
@RequestMapping(value = "/positions")
public class PositionController {
    
    @Autowired
    private PositionService service;

    @GetMapping
    public ResponseEntity<Set<PositionDTO>> findAll() {
        Set<PositionDTO> positionDTOs = service.findAll();
        ResponseEntity<Set<PositionDTO>> response = ResponseEntity.ok().body(positionDTOs);
        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PositionDTO> findById(@PathVariable Long id) {
        PositionDTO positionDTO = service.findById(id);
        ResponseEntity<PositionDTO> response = ResponseEntity.ok().body(positionDTO);
        return response;
    }

    @PostMapping
    public ResponseEntity<PositionDTO> save(@RequestBody PostPositionDTO postPositionDTO) {
        PositionDTO positionDTO = service.save(postPositionDTO);
        URI uri = 
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(positionDTO.getId())
            .toUri();
        ResponseEntity<PositionDTO> response = ResponseEntity.created(uri).body(positionDTO);
        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PositionDTO> updateById(@PathVariable Long id, @RequestBody UpdatePositionDTO updatePositionDTO) {
        PositionDTO positionDTO = service.update(id, updatePositionDTO);
        ResponseEntity<PositionDTO> response = ResponseEntity.ok().body(positionDTO);
        return response;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return response;
    }

}

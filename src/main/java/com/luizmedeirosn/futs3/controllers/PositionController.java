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

import com.luizmedeirosn.futs3.dto.request.post.PostPositionDTO;
import com.luizmedeirosn.futs3.dto.request.update.UpdatePositionDTO;
import com.luizmedeirosn.futs3.dto.response.min.PositionMinDTO;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
import com.luizmedeirosn.futs3.services.PositionService;

@RestController
@RequestMapping(value = "/positions")
public class PositionController {
    
    @Autowired
    private PositionService positionService;

    @GetMapping
    public ResponseEntity<List<PositionMinDTO>> findAll() {
        return ResponseEntity.ok().body(positionService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PositionMinDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(positionService.findById(id));
    }

    @GetMapping(value = "/{id}/parameters")
    public ResponseEntity<List<PositionParametersProjection>> findAllPositionParameters(@PathVariable Long id) {
        return ResponseEntity.ok().body(positionService.findAllPositionParameters(id));
    }

    @PostMapping
    public ResponseEntity<PositionMinDTO> save(@RequestBody PostPositionDTO postPositionDTO) {
        PositionMinDTO positionDTO = positionService.save(postPositionDTO);
        URI uri = 
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(positionDTO.getId())
            .toUri();
        return ResponseEntity.created(uri).body(positionDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PositionMinDTO> updateById(@PathVariable Long id, @RequestBody UpdatePositionDTO updatePositionDTO) {
        return ResponseEntity.ok().body(positionService.update(id, updatePositionDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        positionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

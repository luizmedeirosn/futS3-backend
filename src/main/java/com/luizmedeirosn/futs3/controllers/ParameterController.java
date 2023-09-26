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

import com.luizmedeirosn.futs3.dto.input.post.PostParameterDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdateParameterDTO;
import com.luizmedeirosn.futs3.dto.output.ParameterDTO;
import com.luizmedeirosn.futs3.services.ParameterSerivce;

@RestController
@RequestMapping(value = "/parameters")
public class ParameterController {
    
    @Autowired
    private ParameterSerivce parameterSerivce;

    @GetMapping
    public ResponseEntity<List<ParameterDTO>> findAll() {
        return ResponseEntity.ok().body(parameterSerivce.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ParameterDTO> findById(@PathVariable Long id) {
        return  ResponseEntity.ok().body(parameterSerivce.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParameterDTO> save(@RequestBody PostParameterDTO postParameterDTO) {
        ParameterDTO parameterDTO = parameterSerivce.save(postParameterDTO);
        URI uri = 
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(parameterDTO.getId())
            .toUri();
        return ResponseEntity.created(uri).body(parameterDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ParameterDTO> updateById(@PathVariable Long id, @RequestBody UpdateParameterDTO updateParameterDTO) {
        return ResponseEntity.ok().body(parameterSerivce.update(id, updateParameterDTO));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        parameterSerivce.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
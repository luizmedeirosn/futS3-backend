package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.services.PlayerService;
import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PlayerMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<Page<PlayerMinResponseDTO>> findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = "name") String sortField,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {
        String field = sortField.toLowerCase();
        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, field));
        return ResponseEntity.ok().body(playerService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(playerService.findById(id));
    }

    @GetMapping("/findByKeyword")
    public ResponseEntity<Page<PlayerMinResponseDTO>> findByKeyword(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("name"));
        return ResponseEntity.ok().body(playerService.findByKeyword(keyword, pageRequest));
    }

    @GetMapping(value = "/picture/{id}")
    public ResponseEntity<ByteArrayResource> findPictureById(@PathVariable @NonNull Long id) {
        PlayerPicture playerPicture = playerService.findPictureById(id);
        byte[] content = playerPicture.getContent();
        ByteArrayResource body;

        if (content != null) {
            body = new ByteArrayResource(content);
        } else {
            throw new EntityNotFoundException("Player without a picture");
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, playerPicture.getContentType()).body(body);
    }

    @PostMapping
    public ResponseEntity<PlayerResponseDTO> save(@ModelAttribute @Valid PlayerRequestDTO playerRequestDTO) {
        PlayerResponseDTO playerResponseDTO = playerService.save(playerRequestDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(playerResponseDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(playerResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PlayerResponseDTO> update(
            @PathVariable @NonNull Long id,
            @ModelAttribute @Valid PlayerRequestDTO playerRequestDTO
    ) {
        return ResponseEntity.ok().body(playerService.update(id, playerRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        playerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

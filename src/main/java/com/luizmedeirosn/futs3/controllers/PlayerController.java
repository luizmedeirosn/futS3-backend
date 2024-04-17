package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.services.PlayerService;
import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PlayerMinResponseDTO;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Cacheable(value = "players")
    @GetMapping
    public ResponseEntity<List<PlayerMinResponseDTO>> findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok().body(playerService.findAll(pageable));
    }

    @Cacheable(value = "players")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(playerService.findById(id));
    }

    @Cacheable(value = "players")
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

    @CacheEvict(value = "players")
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

    @CacheEvict(value = "players")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PlayerResponseDTO> update(
            @PathVariable @NonNull Long id,
            @ModelAttribute @Valid PlayerRequestDTO playerRequestDTO
    ) {
        return ResponseEntity.ok().body(playerService.update(id, playerRequestDTO));
    }

    @CacheEvict(value = "players")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        playerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.services.GameModeService;
import com.luizmedeirosn.futs3.shared.dto.request.GameModeRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.GameModeResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerFullDataResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.GameModeMinResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/gamemodes")
public class GameModeController {

    private final GameModeService gameModeService;

    public GameModeController(GameModeService gameModeService) {
        this.gameModeService = gameModeService;
    }

    @GetMapping
    public ResponseEntity<Page<GameModeMinResponseDTO>> findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("formationName"));
        return ResponseEntity.ok().body(gameModeService.findAll(pageRequest));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GameModeResponseDTO> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok().body(gameModeService.findById(id));
    }

    @GetMapping("/ranking")
    public ResponseEntity<Page<PlayerFullDataResponseDTO>> getPlayersRanking(
            @RequestParam("gameModeId") @NonNull Long gameModeId,
            @RequestParam("positionId") @NonNull Long positionId,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("totalScore"));
        return ResponseEntity.ok().body(gameModeService.getPlayersRanking(gameModeId, positionId, pageRequest));
    }

    @PostMapping
    public ResponseEntity<GameModeResponseDTO> save(@RequestBody @Valid GameModeRequestDTO gameModeRequestDTO) {
        GameModeResponseDTO gameModeResponseDTO = gameModeService.save(gameModeRequestDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gameModeResponseDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(gameModeResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GameModeResponseDTO> updateById(
            @PathVariable @NonNull Long id,
            @RequestBody @Valid GameModeRequestDTO gameModeRequestDTO
    ) {
        return ResponseEntity.ok().body(gameModeService.update(id, gameModeRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        gameModeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.luizmedeirosn.futs3.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luizmedeirosn.futs3.projections.gamemode.AllGameModesProjection;
import com.luizmedeirosn.futs3.projections.gamemode.GameModePositionProjection;
import com.luizmedeirosn.futs3.services.GameModeService;
import com.luizmedeirosn.futs3.shared.dto.request.GameModeRequestDTO;
import com.luizmedeirosn.futs3.shared.dto.response.GameModeResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerFullScoreResponseDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.GameModeMinResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/gamemodes")
@RequiredArgsConstructor
public class GameModeController {

    private final GameModeService gameModeService;

    @GetMapping
    public ResponseEntity<List<GameModeMinResponseDTO>> findAll() {
        return ResponseEntity.ok().body(gameModeService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GameModeMinResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(gameModeService.findById(id));
    }

    @GetMapping(value = "/full")
    public ResponseEntity<List<AllGameModesProjection>> findAllFull() {
        return ResponseEntity.ok().body(gameModeService.findAllFull());
    }

    @GetMapping(value = "/{id}/full")
    public ResponseEntity<GameModeResponseDTO> findFullById(@PathVariable Long id) {
        return ResponseEntity.ok().body(gameModeService.findFullById(id));
    }

    @GetMapping(value = "/{id}/positions")
    public ResponseEntity<List<GameModePositionProjection>> findGameModePositions(@PathVariable Long id) {
        return ResponseEntity.ok().body(gameModeService.findGameModePositions(id));
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<PlayerFullScoreResponseDTO>> getPlayersRanking(
            @RequestParam("gameModeId") Long gameModeId,
            @RequestParam("positionId") Long positionId) {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ResponseEntity.ok().body(gameModeService.getPlayersRanking(gameModeId, positionId));
    }

    @PostMapping
    public ResponseEntity<GameModeResponseDTO> save(@RequestBody GameModeRequestDTO gameModeRequestDTO) {
        GameModeResponseDTO gameModeResponseDTO = gameModeService.save(gameModeRequestDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gameModeResponseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(gameModeResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GameModeMinResponseDTO> updateById(@PathVariable Long id,
            @RequestBody GameModeRequestDTO gameModeRequestDTO) {
        return ResponseEntity.ok().body(gameModeService.update(id, gameModeRequestDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        gameModeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

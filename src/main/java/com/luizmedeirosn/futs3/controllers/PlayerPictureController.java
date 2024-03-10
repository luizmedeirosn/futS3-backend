package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.services.PlayerPictureService;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/playerspictures")
public class PlayerPictureController {

    private final PlayerPictureService playerPictureService;

    public PlayerPictureController(PlayerPictureService playerPictureService) {
        this.playerPictureService = playerPictureService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ByteArrayResource> findById(@PathVariable @NonNull Long id) {
        PlayerPicture playerPicture = playerPictureService.findById(id);
        byte[] content = playerPicture.getContent();
        ByteArrayResource body = null;

        if (content != null) {
            body = new ByteArrayResource(content);
        } else {
            throw new EntityNotFoundException("Player without a picture");
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, playerPicture.getContentType()).body(body);
    }

}

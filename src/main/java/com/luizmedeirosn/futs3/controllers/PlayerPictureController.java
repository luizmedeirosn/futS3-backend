package com.luizmedeirosn.futs3.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.services.PlayerPictureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/playerspictures")
@RequiredArgsConstructor
public class PlayerPictureController {

    private final PlayerPictureService playerPictureService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ByteArrayResource> findById(@PathVariable Long id) {
        PlayerPicture playerPicture = playerPictureService.findById(id);
        ByteArrayResource body = new ByteArrayResource(playerPicture.getContent());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, playerPicture.getContentType()).body(body);
    }

}

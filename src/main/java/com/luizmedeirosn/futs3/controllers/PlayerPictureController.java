package com.luizmedeirosn.futs3.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.services.PlayerPictureService;
import com.luizmedeirosn.futs3.shared.dto.response.PlayerPictureDTO;

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

    @PostMapping
    public PlayerPictureDTO save(@RequestPart MultipartFile file) {
        PlayerPicture playerPicture = playerPictureService.save(file);
        String pictureLink = PlayerPictureService.createPictureLink(playerPicture.getId());
        return new PlayerPictureDTO(file.getOriginalFilename(), pictureLink);
    }

}

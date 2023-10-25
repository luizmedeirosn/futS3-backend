package com.luizmedeirosn.futs3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luizmedeirosn.futs3.dto.response.PlayerPictureDTO;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.services.PlayerPictureService;

@RestController
@RequestMapping(value = "/playerspictures")
public class PlayerPictureController {
    
    @Autowired
    private PlayerPictureService playerPictureService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ByteArrayResource> findById(@PathVariable Long id) {
        PlayerPicture playerPicture = playerPictureService.findById(id);
        ByteArrayResource body = new ByteArrayResource(playerPicture.getData());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, playerPicture.getType()).body(body);
    }

    @PostMapping
    public PlayerPictureDTO save(@RequestPart MultipartFile file) {
        PlayerPicture playerPicture = playerPictureService.save(file);
        return new PlayerPictureDTO(file.getOriginalFilename(), createPictureLink(playerPicture.getId()));
    }

    private String createPictureLink(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/playerspictures/" + id).toUriString();
    }

}

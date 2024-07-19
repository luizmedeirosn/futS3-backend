package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.services.CustomUserService;
import com.luizmedeirosn.futs3.shared.dto.response.CustomUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class CustomUserController {

  private final CustomUserService customUserService;

  public CustomUserController(CustomUserService customUserService) {
    this.customUserService = customUserService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Page<CustomUserDTO>> findAll(
      @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
    return ResponseEntity.ok()
        .body(customUserService.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("username"))));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<CustomUserDTO> findById(@PathVariable @NonNull Long id) {
    return ResponseEntity.ok().body(customUserService.findById(id));
  }
}

package com.forumhub.Forum_Hub_Desafio_Alura.controller;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.User;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.UserRepository;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.CreateUserDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.UserResponseDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.UserUpdateDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions.NotFound;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid CreateUserDTO userData) {
        var user = userRepository.save(new User(userData));

        return ResponseEntity.ok().body(new UserResponseDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFound("User not found"));
        return ResponseEntity.ok().body(new UserResponseDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getUsers(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        var page = userRepository.findAllByIsActiveTrue(pagination).map(UserResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserUpdateDTO userData, @PathVariable Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFound("User not found"));
        user.update(userData);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFound("User not found"));
        user.delete();

        return ResponseEntity.noContent().build();
    }
}

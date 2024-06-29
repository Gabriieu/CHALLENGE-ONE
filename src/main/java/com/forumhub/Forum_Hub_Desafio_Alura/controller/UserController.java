package com.forumhub.Forum_Hub_Desafio_Alura.controller;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.User;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.UserRepository;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.CreateUserDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.UserResponseDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.UserUpdateDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions.Forbidden;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions.NotFound;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid CreateUserDTO userData) {
        String encodedPassword = passwordEncoder.encode(userData.password());
        var user = new User(userData, encodedPassword);
        userRepository.save(user);

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
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserUpdateDTO userData,
                                                  @PathVariable Long id,
                                                  Authentication authentication) {
        var userRequestId = authentication.getCredentials().toString();
        var isAdmin = authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        var user = userRepository.findById(id).orElseThrow(() -> new NotFound("User not found"));
        if (!user.getId().toString().equals(userRequestId) && !isAdmin) {
            throw new Forbidden("You do not have permission to perform this action");
        }

        if(userData.password() != null){
            String encodedPassword = passwordEncoder.encode(userData.password());
            user.setHash(encodedPassword);
        }
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

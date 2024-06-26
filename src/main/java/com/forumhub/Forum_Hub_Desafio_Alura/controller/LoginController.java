package com.forumhub.Forum_Hub_Desafio_Alura.controller;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.User;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto.LoginDTO;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.security.DadosTokenJWT;
import com.forumhub.Forum_Hub_Desafio_Alura.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {


    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.manager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid LoginDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.password());
        var authentication = manager.authenticate(authenticationToken);
        var jwtToken = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(jwtToken));
    }
}

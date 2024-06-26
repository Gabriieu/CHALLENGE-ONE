package com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @Email
        String email,
        @NotBlank
        String password
) {
}

package com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(
        String name,
        @Email
        String email,
        String password
) {
}

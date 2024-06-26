package com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.User;

public record UserResponseDTO(
        Long id,
        String name
) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getName());
    }
}

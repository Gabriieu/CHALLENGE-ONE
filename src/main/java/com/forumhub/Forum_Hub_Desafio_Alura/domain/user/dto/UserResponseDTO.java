package com.forumhub.Forum_Hub_Desafio_Alura.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.user.User;

public record UserResponseDTO(
        Long id,
        String name,

        @JsonProperty("is_active")
        boolean isActive
) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getName(), user.isActive());
    }
}

package com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicDTO(
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String text,

        @NotNull
        Long course

) {

    public CreateTopicDTO(String title, String text, Long course) {
        this(null, title, text, course);
    }
}

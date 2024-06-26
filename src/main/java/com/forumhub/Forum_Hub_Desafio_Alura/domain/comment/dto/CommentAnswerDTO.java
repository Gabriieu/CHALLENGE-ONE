package com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentAnswerDTO(
        Long id,
        @NotBlank
        String text
) {
    public CommentAnswerDTO(String text, Long topic) {
        this(null, text);
    }
}

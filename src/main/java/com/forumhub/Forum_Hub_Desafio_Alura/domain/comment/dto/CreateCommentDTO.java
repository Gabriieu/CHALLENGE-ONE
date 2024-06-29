package com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentDTO(
        @NotBlank
        String text
) {
}

package com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forumhub.Forum_Hub_Desafio_Alura.domain.comment.Comment;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        Long id,
        String text,
        @JsonProperty("topic_id")
        Long topicId,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("author")
        String author,

        boolean solution
) {

    public CommentResponseDTO(Comment answer) {
        this(answer.getId(),
                answer.getText(),
                answer.getTopic().getId(),
                answer.getCreatedAt(),
                answer.getUser().getName(),
                answer.isSolution());
    }
}

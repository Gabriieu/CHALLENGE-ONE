package com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.dto;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.topic.Status;

public record UpdateTopicDTO(
        Long id,
        String title,
        String text
) {
}

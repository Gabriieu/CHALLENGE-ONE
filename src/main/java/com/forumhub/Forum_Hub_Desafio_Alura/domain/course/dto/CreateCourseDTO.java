package com.forumhub.Forum_Hub_Desafio_Alura.domain.course.dto;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.Categories;
import jakarta.validation.constraints.NotBlank;

public record CreateCourseDTO(
        @NotBlank
        String name,
        Categories category
) {
}

package com.forumhub.Forum_Hub_Desafio_Alura.domain.course.dto;

import com.forumhub.Forum_Hub_Desafio_Alura.domain.course.Categories;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public record UpdateCourseDTO(
        String name,
        Categories category
) {
}

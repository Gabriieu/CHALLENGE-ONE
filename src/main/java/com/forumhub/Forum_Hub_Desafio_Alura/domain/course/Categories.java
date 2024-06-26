package com.forumhub.Forum_Hub_Desafio_Alura.domain.course;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Categories {
    BACKEND,
    FRONTEND,
    MOBILE,
    DEVOPS,
    UI_UX_DESIGN,
    DATA_SCIENCE,
    GESTAO_INOVACAO,
    INTELIGENCIA_ARTIFICIAL;

    @JsonCreator
    public static Categories fromString(String value) {
        for (Categories category : Categories.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + value);
    }
}

package com.forumhub.Forum_Hub_Desafio_Alura.domain.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


@Getter
public enum Categories {
    BACKEND("Backend"),
    FRONTEND("Frontend"),
    MOBILE("Mobile"),
    DEVOPS("DevOps"),
    UI_UX_DESIGN("UI/UX Design"),
    DATA_SCIENCE("Data Science"),
    GESTAO_INOVACAO("Gestão e Inovação"),
    INTELIGENCIA_ARTIFICIAL("Inteligência Artificial");

    private final String displayName;

    Categories(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Categories fromString(String value) {
        for (Categories category : Categories.values()) {
            if (category.displayName.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + value);
    }
}

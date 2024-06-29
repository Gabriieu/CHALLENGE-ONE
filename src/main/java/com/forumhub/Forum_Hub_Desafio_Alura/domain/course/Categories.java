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
    INTELIGENCIA_ARTIFICIAL("Inteligência Artificial"),
    PROGRAMMING("Programação"),
    WEB_DEVELOPMENT("Desenvolvimento Web"),
    MOBILE_DEVELOPMENT("Desenvolvimento Mobile"),
    SECURITY("Segurança da Informação"),
    CLOUD_COMPUTING("Cloud Computing"),
    DESIGN("Design"),
    BLOCKCHAIN("Blockchain"),
    GAME_DEVELOPMENT("Desenvolvimento de Jogos"),
    MARKETING("Marketing"),
    IOT("IoT"),
    PROJECT_MANAGEMENT("Gestão de Projetos");

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

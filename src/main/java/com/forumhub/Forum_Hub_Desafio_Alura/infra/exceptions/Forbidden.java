package com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class Forbidden extends RuntimeException{

    public Forbidden(String message){
        super(message);
    }
}

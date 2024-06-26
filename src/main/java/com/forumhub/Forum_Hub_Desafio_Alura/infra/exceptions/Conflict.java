package com.forumhub.Forum_Hub_Desafio_Alura.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class Conflict extends RuntimeException{
    public Conflict(String message){
        super(message);
    }
}

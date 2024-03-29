package com.plexus.w2m.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SuperheroNotFoundException extends RuntimeException {

    public SuperheroNotFoundException(Long id) {
        super(String.format("No se encontró el superhéroe para el identificador %d", id));
    }
}
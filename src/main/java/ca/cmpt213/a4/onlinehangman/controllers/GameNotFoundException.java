package ca.cmpt213.a4.onlinehangman.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

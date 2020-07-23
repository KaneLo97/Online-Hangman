package ca.cmpt213.a4.onlinehangman.controllers;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

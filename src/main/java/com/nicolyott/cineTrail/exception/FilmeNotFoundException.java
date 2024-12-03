package com.nicolyott.cineTrail.exception;

public class FilmeNotFoundException extends RuntimeException{

    public FilmeNotFoundException(String message){
        super("Id de filme invalido");
    }

    public FilmeNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

}

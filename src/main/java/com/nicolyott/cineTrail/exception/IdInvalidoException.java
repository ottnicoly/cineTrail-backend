package com.nicolyott.cineTrail.exception;

public class IdInvalidoException extends RuntimeException{

    public IdInvalidoException(String message){
        super("Id de filme invalido");
    }

    public IdInvalidoException(String message, Throwable cause){
        super(message, cause);
    }

}

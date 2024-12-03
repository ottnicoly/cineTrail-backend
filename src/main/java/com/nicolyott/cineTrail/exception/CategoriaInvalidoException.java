package com.nicolyott.cineTrail.exception;

public class CategoriaInvalidoException extends RuntimeException{

    public CategoriaInvalidoException(String message){
        super("Categoria invalida!");
    }

    public CategoriaInvalidoException(String message, Throwable cause){
        super(message, cause);
    }

}

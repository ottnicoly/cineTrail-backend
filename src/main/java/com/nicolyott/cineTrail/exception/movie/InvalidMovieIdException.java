package com.nicolyott.cineTrail.exception.movie;

public class InvalidMovieIdException extends RuntimeException{

    public InvalidMovieIdException(){
        super("nenhum filme com esse id foi encontrado");
    }

    public InvalidMovieIdException(String message){
        super(message);
    }
}

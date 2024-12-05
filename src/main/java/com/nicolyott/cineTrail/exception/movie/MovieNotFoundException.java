package com.nicolyott.cineTrail.exception.movie;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(){
        super("Nenhum filme encontrado");
    }

    public MovieNotFoundException(String message){
        super(message);
    }

}

package com.nicolyott.cineTrail.exception.favoriteMovie;

public class DuplicateFavoriteMovieException extends RuntimeException{

    public DuplicateFavoriteMovieException (){
        super("Este filme já está nos seus favoritos");
    }

    public DuplicateFavoriteMovieException (String message){
        super(message);
    }

}

package com.nicolyott.cineTrail.exception.favoriteMovie;

public class FavoriteMovieNotFoundException extends RuntimeException{

    public FavoriteMovieNotFoundException(Integer idTmdb){
        super("Filme favorito com ID " + idTmdb + " não encontrado");
    }


    public FavoriteMovieNotFoundException(String message){
        super(message);
    }
}

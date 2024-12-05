package com.nicolyott.cineTrail.exception.favoriteMovie;

import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovieCategory;

public class InvalidCategoryException extends RuntimeException{

    public InvalidCategoryException(FavoriteMovieCategory category){
        super("Categoria inválida fornecida: " + category);
    }


    public InvalidCategoryException(String message){
        super(message);
    }
}

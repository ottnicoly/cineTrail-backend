package com.nicolyott.cineTrail.infra;

import com.nicolyott.cineTrail.exception.DuplicateFavoriteMovieException;
import com.nicolyott.cineTrail.exception.FavoriteMovieNotFoundException;
import com.nicolyott.cineTrail.exception.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateFavoriteMovieException.class)
    public ResponseEntity<RestErrorMessage> handleDuplicateFavoriteMovie(DuplicateFavoriteMovieException ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<RestErrorMessage> handleMovieNotFound(MovieNotFoundException ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FavoriteMovieNotFoundException.class)
    public ResponseEntity<RestErrorMessage> handleFavoriteMovieNotFound(FavoriteMovieNotFoundException ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGeneralException(Exception ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

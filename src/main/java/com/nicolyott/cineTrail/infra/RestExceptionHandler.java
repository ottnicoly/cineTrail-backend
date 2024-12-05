package com.nicolyott.cineTrail.infra;

import com.nicolyott.cineTrail.exception.favoriteMovie.DuplicateFavoriteMovieException;
import com.nicolyott.cineTrail.exception.favoriteMovie.FavoriteMovieNotFoundException;
import com.nicolyott.cineTrail.exception.favoriteMovie.InvalidCategoryException;
import com.nicolyott.cineTrail.exception.movie.InvalidMovieIdException;
import com.nicolyott.cineTrail.exception.movie.MovieNotFoundException;
import com.nicolyott.cineTrail.exception.user.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<RestErrorMessage> handleInvalidCategory(InvalidCategoryException ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(InvalidMovieIdException.class)
    public ResponseEntity<RestErrorMessage> handleInvalidMovieId(InvalidMovieIdException ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<RestErrorMessage> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGeneralException(Exception ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

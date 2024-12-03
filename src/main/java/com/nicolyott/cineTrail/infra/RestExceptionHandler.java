package com.nicolyott.cineTrail.infra;

import com.nicolyott.cineTrail.exception.CategoriaInvalidoException;
import com.nicolyott.cineTrail.exception.FilmeNotFoundException;
import com.nicolyott.cineTrail.exception.IdInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> IdInvalidoHandler(IdInvalidoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> CategoriaInvalidoHandler(CategoriaInvalidoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> FilmeNotFoundHandler(FilmeNotFoundException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

}

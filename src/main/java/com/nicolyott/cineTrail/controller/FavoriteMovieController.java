package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovie;
import com.nicolyott.cineTrail.service.movie.FavoriteMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("favorite")
public class FavoriteMovieController {

    @Autowired
    private FavoriteMovieService service;

    @GetMapping()
    public ResponseEntity<List<FavoriteMovie>> getAllFavoriteMovies(){
        List<FavoriteMovie> favoriteMovies = service.getAllFavoriteMovies();
        return new ResponseEntity<>(favoriteMovies, HttpStatus.OK);
    }

    @PostMapping("/save/{idTmdb}/{category}")
    public ResponseEntity<Void> addFavoriteMovie(@PathVariable Integer idTmdb, @PathVariable String category){
        service.addFavoriteMovie(idTmdb, category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{idTmdb}")
    public ResponseEntity<Void> removeFavoriteMovie(@PathVariable Integer idTmdb){
        service.removeFavoriteMovie(idTmdb);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{favoriteCategory}")
    public ResponseEntity<List<FavoriteMovie>> getFavoriteMovieByCategory(@PathVariable String favoriteCategory) {
        List<FavoriteMovie> favoriteMovies = service.getFavoriteMovieByCategory(favoriteCategory);
        return new ResponseEntity<>(favoriteMovies, HttpStatus.OK);
    }

}

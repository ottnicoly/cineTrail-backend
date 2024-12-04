package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovieCategory;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovie;
import com.nicolyott.cineTrail.service.FavoriteMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteMovieController {

    @Autowired
    private FavoriteMovieService service;

    @GetMapping()
    public List<FavoriteMovie> getAllFavoriteMovies(){
        return service.getAllFavoriteMovies();
    }

    @PostMapping("/save/{idTmdb}/{category}")
    public void addFavoriteMovie(@PathVariable Integer idTmdb, @PathVariable FavoriteMovieCategory category){
        service.addFavoriteMovie(idTmdb, category);
    }

    @DeleteMapping("/remove/{idTmdb}")
    public void removeFavoriteMovie(@PathVariable Integer idTmdb){
        service.removeFavoriteMovie(idTmdb);
    }

    @GetMapping("/category/{favoriteCategory}")
    public List<FavoriteMovie> getFavoriteMovieByCategory(@PathVariable String favoriteCategory) {
        return service.getFavoriteMovieByCategory(favoriteCategory);
    }

}

package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovieCategory;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovie;
import com.nicolyott.cineTrail.repository.FavoriteMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class FavoriteMovieService {

    @Autowired
    private FavoriteMovieRepository repository;
    @Autowired
    private MovieService movieService;

    public List<FavoriteMovie> getAllFavoriteMovies(){
        return repository.findAll();
    }

    public ResponseEntity<FavoriteMovie> addFavoriteMovie(Integer movieId, FavoriteMovieCategory category){
       MovieDTO movieDTO = movieService.getMovieById(movieId);
       FavoriteMovie favoriteMovie = new FavoriteMovie(movieDTO, category);
       repository.save(favoriteMovie);
       return ResponseEntity.ok().body(favoriteMovie);
    }

    public ResponseEntity<FavoriteMovie> removeFavoriteMovie(Integer idTmdb){
        FavoriteMovie favoriteMovie = repository.findByIdTmdb(idTmdb);
        repository.delete(favoriteMovie);
        return ResponseEntity.ok().body(favoriteMovie);
    }

    public List<FavoriteMovie> getFavoriteMovieByCategory(@RequestParam String favoriteCategory){
        FavoriteMovieCategory category = FavoriteMovieCategory.valueOf(favoriteCategory.toUpperCase());
        return repository.findByFavoriteMovieCategory(category);
    }

    public boolean isCategoryValid(FavoriteMovieCategory favoriteCategory){
        for(FavoriteMovieCategory category : FavoriteMovieCategory.values()) {
            if (favoriteCategory.equals(category)) {
                return true;
            }
        }
        return false;
    }

}

package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovie;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovieCategory;
import com.nicolyott.cineTrail.entity.user.User;
import com.nicolyott.cineTrail.exception.DuplicateFavoriteMovieException;
import com.nicolyott.cineTrail.exception.FavoriteMovieNotFoundException;
import com.nicolyott.cineTrail.exception.InvalidCategoryException;
import com.nicolyott.cineTrail.repository.FavoriteMovieRepository;
import com.nicolyott.cineTrail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class FavoriteMovieService {

    @Autowired
    private FavoriteMovieRepository repository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private UserRepository userRepository;

    public List<FavoriteMovie> getAllFavoriteMovies(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(username);
        return repository.findByUser(user);
    }

    public ResponseEntity<FavoriteMovie> addFavoriteMovie(Integer movieId, FavoriteMovieCategory category){
       String username = SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userRepository.findByLogin(username);
       FavoriteMovie existsFavoriteMovie = repository.findByIdTmdbAndUser(movieId, user);
        if (existsFavoriteMovie != null) {
            throw new DuplicateFavoriteMovieException("Este filme já está nos seus favoritos");
        }
        if (!isCategoryValid(category)) {
            throw new InvalidCategoryException("Categoria inválida fornecida: " + category);
        }

       MovieDTO movieDTO = movieService.getMovieById(movieId);
       FavoriteMovie favoriteMovie = new FavoriteMovie(movieDTO, category, user);
       repository.save(favoriteMovie);
       return ResponseEntity.ok().body(favoriteMovie);
    }

    public ResponseEntity<FavoriteMovie> removeFavoriteMovie(Integer idTmdb){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(username);
        FavoriteMovie favoriteMovie = repository.findByIdTmdbAndUser(idTmdb, user);
        if (favoriteMovie == null) {
            throw new FavoriteMovieNotFoundException("Filme favorito com ID " + idTmdb + " não encontrado");
        }
        repository.delete(favoriteMovie);
        return ResponseEntity.ok().body(favoriteMovie);
    }

    public List<FavoriteMovie> getFavoriteMovieByCategory(@RequestParam String favoriteCategory){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(username);
        FavoriteMovieCategory category = FavoriteMovieCategory.valueOf(favoriteCategory.toUpperCase());
        return repository.findByFavoriteMovieCategoryAndUser(category, user);
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

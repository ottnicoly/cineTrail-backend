package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovie;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovieCategory;
import com.nicolyott.cineTrail.entity.user.User;
import com.nicolyott.cineTrail.exception.favoriteMovie.DuplicateFavoriteMovieException;
import com.nicolyott.cineTrail.exception.favoriteMovie.FavoriteMovieNotFoundException;
import com.nicolyott.cineTrail.exception.favoriteMovie.InvalidCategoryException;
import com.nicolyott.cineTrail.repository.FavoriteMovieRepository;
import com.nicolyott.cineTrail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<FavoriteMovie> getAllFavoriteMovies() {
        List<FavoriteMovie> favoriteMoviesList = repository.findByUser(getUser());
        emptyFavoriteMovies(favoriteMoviesList);
        return favoriteMoviesList;
    }

    public void addFavoriteMovie(Integer movieId, String category) {
        FavoriteMovieCategory enumCategory = isCategoryValid(category);
        existsFavoritMovie(movieId);

        MovieDTO movieDTO = movieService.getMovieById(movieId);
        FavoriteMovie favoriteMovie = new FavoriteMovie(movieDTO, enumCategory, getUser());

        repository.save(favoriteMovie);
    }

    public void removeFavoriteMovie(Integer idTmdb) {
        FavoriteMovie favoriteMovie = repository.findByIdTmdbAndUser(idTmdb, getUser());
        if (favoriteMovie == null) {
            throw new FavoriteMovieNotFoundException(idTmdb);
        }
        repository.delete(favoriteMovie);
    }

    public List<FavoriteMovie> getFavoriteMovieByCategory(@RequestParam String favoriteCategory) {
        FavoriteMovieCategory category = isCategoryValid(favoriteCategory);
        List<FavoriteMovie> favoriteMovieList = repository.findByFavoriteMovieCategoryAndUser(category, getUser());
        emptyFavoriteMovies(favoriteMovieList);
        return favoriteMovieList;
    }

    public FavoriteMovieCategory isCategoryValid(String category) {
        try {
            return FavoriteMovieCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCategoryException(category);
        }
    }

    public void existsFavoritMovie(Integer movieId) {
        FavoriteMovie existsFavoriteMovie = repository.findByIdTmdbAndUser(movieId, getUser());

        if (existsFavoriteMovie != null) {
            throw new DuplicateFavoriteMovieException();
        }
    }

    public void emptyFavoriteMovies(List<FavoriteMovie> favoriteMovieList){
        if(favoriteMovieList.isEmpty()){
            throw new FavoriteMovieNotFoundException("A lista de filmes favoritos está vazia");
        }
    }

    public User getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(username);
        return user;
    }


}

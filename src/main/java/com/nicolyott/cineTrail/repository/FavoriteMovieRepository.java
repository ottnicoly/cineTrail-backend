package com.nicolyott.cineTrail.repository;

import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovieCategory;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovie;
import com.nicolyott.cineTrail.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

   List<FavoriteMovie> findByUser(User user);

   FavoriteMovie findByIdTmdbAndUser(Integer idTmdb, User user);

   List<FavoriteMovie> findByFavoriteMovieCategoryAndUser(FavoriteMovieCategory favoriteCategory, User user);

}

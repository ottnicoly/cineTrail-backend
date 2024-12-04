package com.nicolyott.cineTrail.repository;

import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovieCategory;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

   FavoriteMovie findByIdTmdb(Integer idTmdb);

   List<FavoriteMovie> findByFavoriteMovieCategory(FavoriteMovieCategory favoriteCategory);

}

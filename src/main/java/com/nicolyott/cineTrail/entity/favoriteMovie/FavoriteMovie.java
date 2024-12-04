package com.nicolyott.cineTrail.entity.favoriteMovie;

import com.nicolyott.cineTrail.dto.MovieDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favorite_movies")
public class FavoriteMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer idTmdb;
    private String title;
    private LocalDateTime favoriteDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FavoriteMovieCategory favoriteMovieCategory;

    public FavoriteMovie(MovieDTO movieDTO, FavoriteMovieCategory favoriteMovieCategory){
        this.idTmdb = movieDTO.idTmdb();
        this.title = movieDTO.name();
        this.favoriteDate = LocalDateTime.now();
        this.favoriteMovieCategory = favoriteMovieCategory;

    }
}

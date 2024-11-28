package com.nicolyott.cineTrail.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String premiereDate;
    private String runtime;
    private String category;
    private String overview;
    private String poster;

    public Movie(){

    }

    public Movie(MovieData movieData) {
        this.title = movieData.title();
        this.premiereDate = movieData.premiereDate();
        this.overview = movieData.overview();
        this.poster = movieData.poster();
    }
}

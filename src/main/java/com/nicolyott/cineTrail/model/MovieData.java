package com.nicolyott.cineTrail.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieData(
        @JsonAlias("title") String title,
        @JsonAlias("release_date") String premiereDate,
//        @JsonAlias("Runtime") String runtime,
//        @JsonAlias("Genre") String category,
        @JsonAlias("overview") String overview,
        @JsonAlias("poster_path") String poster
        ) {
}

package com.nicolyott.cineTrail.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieDTO(
        @JsonAlias("original_title") String name,
        @JsonAlias("overview") String overview,
        @JsonAlias("popularity") String popularity,
        @JsonAlias("poster_path") String poster,
        @JsonAlias("release_date") String releaseDate,
        @JsonAlias("id") Integer idTmdb
) {}

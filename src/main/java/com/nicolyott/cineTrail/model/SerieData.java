package com.nicolyott.cineTrail.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieData(
        @JsonAlias("Title") String title,
        @JsonAlias("Released") String premiereDate,
        @JsonAlias("Runtime") String runtime,
        @JsonAlias("Genre") String category,
        @JsonAlias("Plot") String overview,
        @JsonAlias("Poster") String poster
) {
}

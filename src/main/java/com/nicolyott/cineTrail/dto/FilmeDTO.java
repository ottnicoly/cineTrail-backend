package com.nicolyott.cineTrail.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FilmeDTO(
        @JsonAlias("original_title") String titulo,
        @JsonAlias("overview") String sinopse,
        @JsonAlias("popularity") String popularidade,
        @JsonAlias("poster_path") String poster,
        @JsonAlias("release_date") String dataLancamento
) {}

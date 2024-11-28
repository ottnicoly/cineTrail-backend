package com.nicolyott.cineTrail.dto;

public record SerieDTO (
        Long id,
        String title,
        String premiereDate,
        String runtime,
        String category,
        String overview,
        String poster
){
}

package com.nicolyott.cineTrail.dto;

public record MovieDTO(
        Long id,
        String title,
        String premiereDate,
        String runtime,
        String overview
){
}

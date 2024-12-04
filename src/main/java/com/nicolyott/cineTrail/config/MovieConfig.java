package com.nicolyott.cineTrail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MovieConfig {

    private final String TMDB_API_KEY;
    private final String TMDB_BASE_URL;

    public MovieConfig (
            @Value("${TMDB_API_KEY}") String TMDB_API_KEY,
            @Value("${TMDB_BASE_URL}")String TMDB_BASE_URL
            ){

        this.TMDB_API_KEY = TMDB_API_KEY;
        this.TMDB_BASE_URL = TMDB_BASE_URL;
    }

    public String getTMDB_API_KEY() {
        return TMDB_API_KEY;
    }

    public String getTMDB_BASE_URL() {
        return TMDB_BASE_URL;
    }
}

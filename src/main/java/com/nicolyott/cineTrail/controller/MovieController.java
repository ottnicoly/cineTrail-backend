package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consulta")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/name/{movie}")
    public List<MovieDTO> getMovieByName(@PathVariable("movie") String movie) {
        return service.getMovieByName(movie);
    }

    @GetMapping("/trending")
    public List<MovieDTO> getTrendingMovies() {
        return service.getTrendingMovies();
    }

    @GetMapping("/id/{id}")
    public MovieDTO getMovieById(@PathVariable("id") Integer idTmdb) {
        return service.getMovieById(idTmdb);
    }

}

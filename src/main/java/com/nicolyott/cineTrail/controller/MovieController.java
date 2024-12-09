package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("query")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/name/{movie}")
    public ResponseEntity<List<MovieDTO>> getMovieByName(@PathVariable String movie) {
        List<MovieDTO> movieDTOList = service.getMovieByName(movie);
        return new ResponseEntity<>(movieDTOList, HttpStatus.OK);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<MovieDTO>> getTrendingMovies() {
        List<MovieDTO> movieDTOList = service.getTrendingMovies();
        return new ResponseEntity<>(movieDTOList, HttpStatus.OK);
    }

    @GetMapping("/id/{idTmdb}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Integer idTmdb) {
        MovieDTO movieDTO = service.getMovieById(idTmdb);
        return ResponseEntity.ok().body(movieDTO);
    }

}

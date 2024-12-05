package com.nicolyott.cineTrail.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolyott.cineTrail.config.MovieConfig;
import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.exception.InvalidMovieIdException;
import com.nicolyott.cineTrail.exception.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    MovieConfig movieConfig;

    ApiService apiService = new ApiService();
    DataConverter dataConverter = new DataConverter();

    public List<MovieDTO> getMovieByName(String movie) {
        String json = apiService
                .dataFetcher(movieConfig.getTMDB_BASE_URL() + "search/movie" + movieConfig.getTMDB_API_KEY() + "&query=" + movie.replaceAll(" ", "+"));
        List<MovieDTO> movieDTOList = getMovieList(json);

        return movieDTOList;
    }

    public List<MovieDTO> getTrendingMovies() {
        String json = apiService.dataFetcher(movieConfig.getTMDB_BASE_URL() + "movie/popular" + movieConfig.getTMDB_API_KEY());
        List<MovieDTO> movieDTOList = getMovieList(json);

        return movieDTOList;
    }

    public MovieDTO getMovieById(Integer idTmdb){
        if (idTmdb <= 0) {
            throw new InvalidMovieIdException("ID de filme inválido: " + idTmdb);
        }
        String json = apiService.dataFetcher(movieConfig.getTMDB_BASE_URL() + "movie/" + idTmdb + movieConfig.getTMDB_API_KEY());
        MovieDTO movieDTO = dataConverter.convertData(json, MovieDTO.class);
        if (movieDTO == null) {
            throw new MovieNotFoundException("Filme com ID " + idTmdb + " não encontrado");
        }
        return movieDTO;
    }

    public List<MovieDTO> getMovieList(String json) {
        Map<String, Object> resp = dataConverter.convertData(json, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) resp.get("results");

        List<MovieDTO> movieDTOList = results.stream()
                .map(result -> {
                    try {
                        return dataConverter
                                .convertData(new ObjectMapper().writeValueAsString(result), MovieDTO.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return movieDTOList;
    }

}

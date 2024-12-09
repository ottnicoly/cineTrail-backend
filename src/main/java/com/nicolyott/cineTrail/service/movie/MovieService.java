package com.nicolyott.cineTrail.service.movie;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolyott.cineTrail.config.MovieConfig;
import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.exception.movie.InvalidMovieIdException;
import com.nicolyott.cineTrail.exception.movie.MovieNotFoundException;
import com.nicolyott.cineTrail.service.api.ApiService;
import com.nicolyott.cineTrail.service.api.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    MovieConfig movieConfig;

    @Autowired
    ApiService apiService;

    @Autowired
    DataConverter dataConverter;

    public List<MovieDTO> getMovieByName(String movie) {
        String json = apiService
                .fetchData(movieConfig.getTMDB_BASE_URL() + "search/movie" + movieConfig.getTMDB_API_KEY() + "&query=" + movie.replaceAll(" ", "+"));
        List<MovieDTO> movieDTOList = getMovieList(json);
        if (movieDTOList.isEmpty()) {
            throw new MovieNotFoundException();
        }
        return movieDTOList;
    }

    public List<MovieDTO> getTrendingMovies() {
        String json = apiService.fetchData(movieConfig.getTMDB_BASE_URL() + "movie/popular" + movieConfig.getTMDB_API_KEY());
        List<MovieDTO> movieDTOList = getMovieList(json);
        return movieDTOList;
    }

    public MovieDTO getMovieById(Integer idTmdb) {
        if(!verificarId(idTmdb)){
            throw new InvalidMovieIdException();
        }

        String json = apiService.fetchData(movieConfig.getTMDB_BASE_URL() + "movie/" + idTmdb + movieConfig.getTMDB_API_KEY());
        MovieDTO movieDTO = dataConverter.convertData(json, MovieDTO.class);
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

    public boolean verificarId(Integer idTmdb) {
        String url = movieConfig.getTMDB_BASE_URL() + "movie/" + idTmdb + movieConfig.getTMDB_API_KEY();
        int statucCode = apiService.checkRequest(url, "GET");
        return statucCode == 200;
    }
}

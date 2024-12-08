package com.nicolyott.cineTrail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolyott.cineTrail.config.MovieConfig;
import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.exception.movie.InvalidMovieIdException;
import com.nicolyott.cineTrail.exception.movie.MovieNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    MovieConfig movieConfig;

    @Mock
    ApiService apiService;

    @Mock
    DataConverter dataConverter;

    @Spy
    @InjectMocks
    MovieService movieService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a list of movies by name when API returns results")
    void getMovieByNameCase1() {
        String movieName = "Inside out";
        String jsonMock = "{\"result\": [ " +
                "{\"name\": \"Inside out\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1234\"} , " +
                "{\"name\": \"Inside out 2\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1235\"} ]}";

        List<MovieDTO> movieList = List.of(
                new MovieDTO("Inside out", "funny movie", "435.986", "poster", "2019", 1234),
                new MovieDTO("Inside out 2", "funny movie", "435.986", "poster", "2019", 1235)
        );

        when(apiService.fetchData(anyString())).thenReturn(jsonMock);

        doReturn(movieList).when(movieService).getMovieList(jsonMock);

        List<MovieDTO> result = movieService.getMovieByName(movieName);

        verify(apiService, times(1)).fetchData(anyString());
        verify(movieService, times(1)).getMovieList(jsonMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Inside out", result.get(0).name());
        Assertions.assertEquals("Inside out 2", result.get(1).name());
    }

    @Test
    @DisplayName("Should throw exception when no movies are found by name")
    void getMovieByNameCase2() {
        String movieName = "Frozen";
        String jsonMock = "{\"result\": []}";

        List<MovieDTO> movieList = List.of(
                new MovieDTO("Inside out", "funny movie", "435.986", "poster", "2019", 1234),
                new MovieDTO("Inside out 2", "funny movie", "435.986", "poster", "2019", 1235)
        );

        when(apiService.fetchData(anyString())).thenReturn(jsonMock);

        doReturn(Collections.emptyList()).when(movieService).getMovieList(jsonMock);

        Assertions.assertThrows(MovieNotFoundException.class, () -> {
            movieService.getMovieByName(movieName);
        });

        verify(apiService, times(1)).fetchData(anyString());
        verify(movieService, times(1)).getMovieList(jsonMock);
    }

    @Test
    @DisplayName("Should return a list of trending movies when API responds with valid data")
    void getTrendingMoviesCase1() {
        String jsonMock = "{\"result\": [ " +
                "{\"name\": \"Inside out\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1234\"} , " +
                "{\"name\": \"Inside out 2\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1235\"} ]}";

        List<MovieDTO> movieList = List.of(
                new MovieDTO("Inside out", "funny movie", "435.986", "poster", "2019", 1234),
                new MovieDTO("Inside out 2", "funny movie", "435.986", "poster", "2019", 1235)
        );

        when(apiService.fetchData(anyString())).thenReturn(jsonMock);
        doReturn(movieList).when(movieService).getMovieList(jsonMock);

        List<MovieDTO> result = movieService.getTrendingMovies();

        verify(apiService, times(1)).fetchData(anyString());
        verify(movieService, times(1)).getMovieList(jsonMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Inside out", result.get(0).name());
        Assertions.assertEquals("Inside out 2", result.get(1).name());
    }

    @Test
    @DisplayName("Should return empty list when no data is returned from the API")
    void getTrendingMoviesCase2() {
        String jsonMock = "{\"result\": []}";

        when(apiService.fetchData(anyString())).thenReturn(jsonMock);
        doReturn(Collections.emptyList()).when(movieService).getMovieList(jsonMock);

        List<MovieDTO> result = movieService.getTrendingMovies();

        verify(apiService, times(1)).fetchData(anyString());
        verify(movieService, times(1)).getMovieList(jsonMock);

        Assertions.assertTrue(result.isEmpty(), "The result list should be empty");
    }

    @Test
    @DisplayName("Should return true when the status code is 200")
    void getMovieByIdCase1() {
        Integer id = 1234;
        String jsonMock = "{\"results\": [ " +
                "{\"name\": \"Inside out\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1234\"}]}";

        when(movieService.verificarId(id)).thenReturn(true);
        when(apiService.fetchData(anyString())).thenReturn(jsonMock);
        when(dataConverter.convertData(jsonMock, MovieDTO.class)).thenReturn(
                new MovieDTO("Inside Out", "funny movie", "435.986", "poster", "2019", 1234)
        );

        MovieDTO result = movieService.getMovieById(id);

        verify(movieService, times(1)).verificarId(id);
        verify(apiService, times(1)).fetchData(anyString());
        verify(dataConverter, times(1)).convertData(jsonMock, MovieDTO.class);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Inside Out", result.name());
        Assertions.assertEquals("funny movie", result.overview());
        Assertions.assertEquals("435.986", result.popularity());
        Assertions.assertEquals("poster", result.poster());
        Assertions.assertEquals("2019", result.releaseDate());
        Assertions.assertEquals(1234, result.idTmdb());
    }

    @Test
    @DisplayName("Should return false when the status code is not 200")
    void getMovieByIdCase2() {
        Integer id = 5432;

        when(movieService.verificarId(id)).thenReturn(false);

        Assertions.assertThrows(InvalidMovieIdException.class, () -> {
            movieService.getMovieById(id);
        });

        verify(movieService, times(1)).verificarId(id);
        verify(apiService, never()).fetchData(anyString());

    }

    @Test
    @DisplayName("Should return list of MovieDTO when JSON contains valid results")
    void getMovieListCase1() {
        String jsonMock = "{\"results\": [ " +
                "{\"name\": \"Inside out\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": 1234}, " +
                "{\"name\": \"Inside out 2\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": 1235} ]}";

        Map<String, Object> mockResponseMap = new HashMap<>();
        mockResponseMap.put("results", List.of(
                Map.of("name", "Inside Out", "overview", "funny movie", "popularity", "435.986", "poster", "poster", "releaseDate", "2019", "idTmdb", "1234"),
                Map.of("name", "Frozen", "overview", "cool movie", "popularity", "400.0", "poster", "poster", "releaseDate", "2010", "idTmdb", "5678")
        ));

        when(dataConverter.convertData(jsonMock, Map.class)).thenReturn(mockResponseMap);
        when(dataConverter.convertData(anyString(), eq(MovieDTO.class))).thenAnswer(invocation -> {
            String jsonStr = invocation.getArgument(0);
            return new ObjectMapper().readValue(jsonStr, MovieDTO.class);
        });

        List<MovieDTO> result = movieService.getMovieList(jsonMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Inside Out", result.get(0).name());
        Assertions.assertEquals("Frozen", result.get(1).name());

        verify(dataConverter, times(1)).convertData(jsonMock, Map.class);
        verify(dataConverter, times(2)).convertData(anyString(), eq(MovieDTO.class));
    }

    @Test
    @DisplayName("Should return empty list when JSON has no results")
    void getMovieListCase2() {
        String jsonMock = "{ \"results\": [] }";

        Map<String, Object> mockResponseMap = new HashMap<>();
        mockResponseMap.put("results", Collections.emptyList());

        when(dataConverter.convertData(jsonMock, Map.class)).thenReturn(mockResponseMap);

        List<MovieDTO> result = movieService.getMovieList(jsonMock);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());

        verify(dataConverter, times(1)).convertData(jsonMock, Map.class);
        verify(dataConverter, never()).convertData(anyString(), eq(MovieDTO.class));
    }

    @Test
    @DisplayName("Should return true when status code is 200")
    void verificarIdCase1() {
        Integer movieId = 12345;
        String baseUrl = movieConfig.getTMDB_BASE_URL();
        String apiKey = movieConfig.getTMDB_API_KEY();
        String url = baseUrl + "movie/" + movieId + apiKey;

        when(movieConfig.getTMDB_BASE_URL()).thenReturn(baseUrl);
        when(movieConfig.getTMDB_API_KEY()).thenReturn(apiKey);

        when(apiService.checkRequest(url, "GET")).thenReturn(200);

        boolean result = movieService.verificarId(movieId);

        Assertions.assertTrue(result, "The method should return true when the status code is 200");

        verify(apiService, times(1)).checkRequest(url, "GET");
    }

    @Test
    @DisplayName("Should return false when status code is not 200")
    void verificarIdCase2() {
        Integer movieId = 12345;
        String baseUrl = movieConfig.getTMDB_BASE_URL();
        String apiKey = movieConfig.getTMDB_API_KEY();
        String url = baseUrl + "movie/" + movieId + apiKey;

        when(movieConfig.getTMDB_BASE_URL()).thenReturn(baseUrl);
        when(movieConfig.getTMDB_API_KEY()).thenReturn(apiKey);

        when(apiService.checkRequest(url, "GET")).thenReturn(404);

        boolean result = movieService.verificarId(movieId);

        Assertions.assertFalse(result, "The method should return false when the status code is not 200");

        verify(apiService, times(1)).checkRequest(url, "GET");
    }

}
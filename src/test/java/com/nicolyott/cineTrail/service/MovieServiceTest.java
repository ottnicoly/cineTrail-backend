package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.config.MovieConfig;
import com.nicolyott.cineTrail.dto.MovieDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    @Autowired
    @InjectMocks
    MovieService movieService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return Movie by name successfully when everything is OK")
    void getMovieByNameCase1() {
        String movieName = "Inside out";
        String jsonMock = "{\"result\": [ " +
                "{\"name\": \"Inside out\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1234\"} , " +
                "{\"name\": \"Inside out 2\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1235\"} ]}";

        List<MovieDTO> movieList = List.of(
                new MovieDTO("Inside out", "funny movie", "435.986", "poster", "2019", 1234),
                new MovieDTO("Inside out 2", "funny movie", "435.986", "poster", "2019", 1235)
        );

        when(apiService.dataFetcher(anyString())).thenReturn(jsonMock);

        doReturn(movieList).when(movieService).getMovieList(jsonMock);

        List<MovieDTO> result = movieService.getMovieByName(movieName);

        verify(apiService, times(1)).dataFetcher(anyString());
        verify(movieService, times(1)).getMovieList(jsonMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Inside out", result.get(0).name());
        Assertions.assertEquals("Inside out 2", result.get(1).name());
    }

    @Test
    void getTrendingMoviesCase1() {
        String jsonMock = "{\"result\": [ " +
                "{\"name\": \"Inside out\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1234\"} , " +
                "{\"name\": \"Inside out 2\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1235\"} ]}";

        List<MovieDTO> movieList = List.of(
                new MovieDTO("Inside out", "funny movie", "435.986", "poster", "2019", 1234),
                new MovieDTO("Inside out 2", "funny movie", "435.986", "poster", "2019", 1235)
        );

        when(apiService.dataFetcher(anyString())).thenReturn(jsonMock);

        doReturn(movieList).when(movieService).getMovieList(jsonMock);

        List<MovieDTO> result = movieService.getTrendingMovies();

        verify(apiService, times(1)).dataFetcher(anyString());
        verify(movieService, times(1)).getMovieList(jsonMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Inside out", result.get(0).name());
        Assertions.assertEquals("Inside out 2", result.get(1).name());
    }

    @Test
    void getMovieByIdCase1() {
        Integer id = 1234;
        String jsonMock = "{\"results\": [ " +
                "{\"name\": \"Inside out\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": \"1234\"}]}";

        MovieDTO movieId = new MovieDTO("Inside out", "funny movie", "435.986", "poster", "2019", 1234);

        when(apiService.dataFetcher(anyString())).thenReturn(jsonMock);

        doReturn(movieId).when(movieService).getMovieById(id);

        MovieDTO result = movieService.getMovieById(id);

        verify(apiService, times(1)).dataFetcher(anyString());
        verify(movieService, times(1)).getMovieList(jsonMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Inside out", result.name());
    }

    @Test
    void getMovieListCase1() {
        String jsonMock = "{\"results\": [ " +
                "{\"name\": \"Inside out\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": 1234}, " +
                "{\"name\": \"Inside out 2\", \"overview\": \"funny movie\", \"popularity\": \"435.986\", \"poster\": \"poster\", \"releaseDate\": \"2019\", \"idTmdb\": 1235} ]}";

        List<MovieDTO> movieList = List.of(
                new MovieDTO("Inside out", "funny movie", "435.986", "poster", "2019", 1234),
                new MovieDTO("Inside out 2", "funny movie", "435.986", "poster", "2019", 1235)
        );

        List<MovieDTO> result = movieService.getMovieList(jsonMock);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(movieList.get(0).name(), result.get(0).name());
        Assertions.assertEquals(movieList.get(1).name(), result.get(1).name());
    }

    @Test
    void emptyMovies() {

    }

    @Test
    void verificarId() {

    }
}
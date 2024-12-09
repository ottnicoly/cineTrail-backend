package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.dto.MovieDTO;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovie;
import com.nicolyott.cineTrail.entity.favoriteMovie.FavoriteMovieCategory;
import com.nicolyott.cineTrail.entity.user.User;
import com.nicolyott.cineTrail.exception.favoriteMovie.DuplicateFavoriteMovieException;
import com.nicolyott.cineTrail.exception.favoriteMovie.FavoriteMovieNotFoundException;
import com.nicolyott.cineTrail.exception.favoriteMovie.InvalidCategoryException;
import com.nicolyott.cineTrail.repository.FavoriteMovieRepository;
import com.nicolyott.cineTrail.repository.UserRepository;
import com.nicolyott.cineTrail.service.movie.FavoriteMovieService;
import com.nicolyott.cineTrail.service.movie.MovieService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class FavoriteMovieServiceTest {

    @Mock
    private FavoriteMovieRepository favoriteMovieRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @InjectMocks
    private FavoriteMovieService favoriteMovieService;

    @Mock
    private MovieDTO movieDTO;

    @Mock
    private FavoriteMovie favoriteMovie;

    @Mock
    private FavoriteMovieCategory favoriteMovieCategory;

    private final List<FavoriteMovie> mockMovieList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockSecurityContext();
    }

    private void mockSecurityContext() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        when(authenticationMock.getName()).thenReturn("mockUser");

        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);

        SecurityContextHolder.setContext(securityContextMock);

        when(userRepository.findByLogin("mockUser")).thenReturn(user);
    }

    @Test
    @DisplayName("Should return a list of favorite movies when repository returns results")
    void getAllFavoriteMoviesCase1() {
        List<FavoriteMovie> mockFavoriteMoviesList = new ArrayList<>();
        mockFavoriteMoviesList.add(new FavoriteMovie());

        when(favoriteMovieRepository.findByUser(user)).thenReturn(mockFavoriteMoviesList);

        List<FavoriteMovie> result = favoriteMovieService.getAllFavoriteMovies();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        verify(favoriteMovieRepository).findByUser(user);
    }

    @Test
    @DisplayName("Should throw FavoriteMovieNotFoundException when favorite movies list is empty")
    void getAllFavoriteMoviesCase2() {
        when(favoriteMovieRepository.findByUser(user)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(FavoriteMovieNotFoundException.class, () -> {
            favoriteMovieService.getAllFavoriteMovies();
        });

        verify(favoriteMovieRepository).findByUser(user);
    }

    @Test
    @DisplayName("Should save a favorite movie when movieId and valid category are passed")
    void addFavoriteMovieCase1() {
        Integer movieId = 123;
        String category = "WATCHED";

        when(favoriteMovieRepository.findByIdTmdbAndUser(movieId, favoriteMovieService.getUser()))
                .thenReturn(null);
        when(movieService.getMovieById(movieId)).thenReturn(movieDTO);

        favoriteMovieService.addFavoriteMovie(movieId, category);

        verify(favoriteMovieRepository).save(any(FavoriteMovie.class));
    }

    @Test
    @DisplayName("Should throw DuplicateFavoriteMovieException if movie already exists for user")
    void addFavoriteMovieCase2() {
        Integer movieId = 123;
        String category = "WATCHED";

        when(favoriteMovieRepository.findByIdTmdbAndUser(movieId, favoriteMovieService.getUser()))
                .thenReturn(new FavoriteMovie());

        Assertions.assertThrows(DuplicateFavoriteMovieException.class, () -> {
            favoriteMovieService.addFavoriteMovie(movieId, category);
        });

        verify(favoriteMovieRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw InvalidCategoryException if category is invalid")
    void addFavoriteMovieCase3() {
        Integer movieId = 123;
        String invalidCategory = "INVALID_CATEGORY";

        when(favoriteMovieRepository.findByIdTmdbAndUser(movieId, favoriteMovieService.getUser()))
                .thenReturn(null);

        Assertions.assertThrows(InvalidCategoryException.class, () -> {
            favoriteMovieService.addFavoriteMovie(movieId, invalidCategory);
        });

        verify(favoriteMovieRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should remove the movie successfully when it exists")
    void removeFavoriteMovieCase1() {
        Integer movieId = 123;

        when(favoriteMovieRepository.findByIdTmdbAndUser(movieId, favoriteMovieService.getUser()))
                .thenReturn(favoriteMovie);

        favoriteMovieService.removeFavoriteMovie(movieId);

        verify(favoriteMovieRepository, times(1)).delete(favoriteMovie);
    }

    @Test
    @DisplayName("Should throw FavoriteMovieNotFoundException when the movie does not exist")
    void removeFavoriteMovieCase2() {
        Integer movieId = 999;

        when(favoriteMovieRepository.findByIdTmdbAndUser(movieId, favoriteMovieService.getUser()))
                .thenReturn(null);

        Assertions.assertThrows(FavoriteMovieNotFoundException.class, () -> {
            favoriteMovieService.removeFavoriteMovie(movieId);
        });

        verify(favoriteMovieRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should return a list of movies when movies exist in the category")
    void getFavoriteMovieByCategoryCase1() {
        String category = "WATCHED";

        when(favoriteMovieRepository.findByFavoriteMovieCategoryAndUser(FavoriteMovieCategory.WATCHED, favoriteMovieService.getUser()))
                .thenReturn(mockMovieList);

        mockMovieList.add(favoriteMovie);

        List<FavoriteMovie> result = favoriteMovieService.getFavoriteMovieByCategory(category);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(favoriteMovieRepository, times(1)).findByFavoriteMovieCategoryAndUser(FavoriteMovieCategory.WATCHED, favoriteMovieService.getUser());
    }

    @Test
    @DisplayName("Should throw FavoriteMovieNotFoundException when the movie list is empty")
    void getFavoriteMovieByCategoryCase2() {
        String category = "WATCHED";

        when(favoriteMovieRepository.findByFavoriteMovieCategoryAndUser(FavoriteMovieCategory.WATCHED, favoriteMovieService.getUser()))
                .thenReturn(new ArrayList<>());

        Assertions.assertThrows(FavoriteMovieNotFoundException.class, () -> {
            favoriteMovieService.getFavoriteMovieByCategory(category);
        });

        verify(favoriteMovieRepository, times(1)).findByFavoriteMovieCategoryAndUser(FavoriteMovieCategory.WATCHED, favoriteMovieService.getUser());
    }

    @Test
    @DisplayName("Should throw InvalidCategoryException when an invalid category is passed")
    void getFavoriteMovieByCategoryCase3() {
        String invalidCategory = "INVALID_CATEGORY";

        Assertions.assertThrows(InvalidCategoryException.class, () -> {
            favoriteMovieService.getFavoriteMovieByCategory(invalidCategory);
        });

        verify(favoriteMovieRepository, never()).findByFavoriteMovieCategoryAndUser(any(), any());
    }

    @Test
    @DisplayName("Should return the correct enum when a valid category is provided")
    void isCategoryValidCase1() {
        String category = "WATCHED";

        FavoriteMovieCategory result = favoriteMovieService.isCategoryValid(category);

        Assertions.assertEquals(FavoriteMovieCategory.WATCHED, result);
    }

    @Test
    @DisplayName("Should return the correct enum when a valid category is provided")
    void isCategoryValidCase2() {
        String invalidCategory = "INVALID_CATEGORY";

        Exception exception = Assertions.assertThrows(InvalidCategoryException.class, () -> {
            favoriteMovieService.isCategoryValid(invalidCategory);
        });

        Assertions.assertEquals("INVALID_CATEGORY", exception.getMessage());
    }

    @Test
    @DisplayName("Should not throw any exception when the list of favorite movies has elements")
    void emptyFavoriteMoviesCase1() {
        List<FavoriteMovie> nonEmptyList = new ArrayList<>();
        nonEmptyList.add(new FavoriteMovie());

        Assertions.assertDoesNotThrow(() -> {
            favoriteMovieService.emptyFavoriteMovies(nonEmptyList);
        });
    }

    @Test
    @DisplayName("Should throw FavoriteMovieNotFoundException when the list of favorite movies is empty")
    void emptyFavoriteMoviesCase2() {
        List<FavoriteMovie> emptyList = new ArrayList<>();

        Assertions.assertThrows(FavoriteMovieNotFoundException.class, () -> {
            favoriteMovieService.emptyFavoriteMovies(emptyList);
        });
    }


    @Test
    @DisplayName("Should return User when SecurityContextHolder has a valid username and userRepository finds the user")
    void getUserCase1() {
        Authentication authMock = mock(Authentication.class);
        when(authMock.getName()).thenReturn("mockUsername");

        SecurityContext securityContextMock = mock(SecurityContext.class);
        when(securityContextMock.getAuthentication()).thenReturn(authMock);
        SecurityContextHolder.setContext(securityContextMock);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("mockUsername");
        when(userRepository.findByLogin("mockUsername")).thenReturn(mockUser);

        User result = favoriteMovieService.getUser();

        Assertions.assertNotNull(result);
        Assertions.assertEquals("mockUsername", result.getLogin());
        verify(userRepository, times(1)).findByLogin("mockUsername");

        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return null when userRepository doesn't find the user")
    void getUserCase2() {
        Authentication authMock = mock(Authentication.class);
        when(authMock.getName()).thenReturn("mockUsername");

        SecurityContext securityContextMock = mock(SecurityContext.class);
        when(securityContextMock.getAuthentication()).thenReturn(authMock);
        SecurityContextHolder.setContext(securityContextMock);

        when(userRepository.findByLogin("mockUsername")).thenReturn(null);

        User result = favoriteMovieService.getUser();

        Assertions.assertNull(result);
        verify(userRepository, times(1)).findByLogin("mockUsername");

        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("  ")
    void getUserCase3() {
        SecurityContext securityContextMock = mock(SecurityContext.class);
        when(securityContextMock.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContextMock);

        Assertions.assertThrows(NullPointerException.class, () -> {
            favoriteMovieService.getUser();
        });

        SecurityContextHolder.clearContext();
    }
}
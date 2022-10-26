package com.rent.store.services;

import com.rent.store.services.impl.MovieServiceImpl;
import com.rent.store.models.dtos.MovieDTO;
import com.rent.store.persistence.entities.Movie;
import com.rent.store.persistence.repositories.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void shouldReturnListOfMovieDTOWhenGetAllMethodIsCalled() {
        final MovieDTO expectedMovieDTO = MovieDTO.builder()
                .uuid("123")
                .build();
        Mockito.when(movieRepository.findAll())
                .thenReturn(Collections.singletonList(Movie.builder()
                        .uuid("123")
                        .build())
                );
        Mockito.when(conversionService.convert(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(expectedMovieDTO);

        final List<MovieDTO> actualMovieDTOs = movieService.getAll();

        Assertions.assertThat(actualMovieDTOs).isNotEmpty();
        Assertions.assertThat(actualMovieDTOs.get(0)).isEqualTo(expectedMovieDTO);
    }

    @Test
    void shouldStoreAndReturnMovieDTOObjectWhenStoreMethodIsCalled() {
        final MovieDTO movieDTOToStore = MovieDTO.builder()
                .uuid("123")
                .build();
        final Movie movieToStore = Movie.builder()
                .uuid("123")
                .build();
        final Movie storedMovie = Movie.builder()
                .id(1L)
                .uuid("123")
                .build();
        final MovieDTO expectedMovieDTO = MovieDTO.builder()
                .uuid("123")
                .build();

        Mockito.when(conversionService.convert(ArgumentMatchers.any(MovieDTO.class), ArgumentMatchers.any()))
                .thenReturn(movieToStore);
        Mockito.when(movieRepository.save(ArgumentMatchers.any())).thenReturn(storedMovie);
        Mockito.when(conversionService.convert(ArgumentMatchers.any(Movie.class), ArgumentMatchers.any()))
                .thenReturn(expectedMovieDTO);

        final MovieDTO actualMovieDTO = movieService.store(movieDTOToStore);

        Assertions.assertThat(actualMovieDTO).isNotNull();
        Assertions.assertThat(actualMovieDTO).isEqualTo(expectedMovieDTO);
    }

    @Test
    void shouldGetAndReturnMovieDTOObjectWhenGetMethodIsCalled() {
        final Movie dbMovie = Movie.builder()
                .id(1L)
                .uuid("123")
                .build();
        final MovieDTO expectedMovieDTO = MovieDTO.builder()
                .uuid("123")
                .build();

        Mockito.when(movieRepository.findByUuid(ArgumentMatchers.anyString())).thenReturn(Optional.of(dbMovie));
        Mockito.when(conversionService.convert(ArgumentMatchers.any(Movie.class), ArgumentMatchers.any()))
                .thenReturn(expectedMovieDTO);

        final MovieDTO actualMovieDTO = movieService.get("123");

        Assertions.assertThat(actualMovieDTO).isNotNull();
        Assertions.assertThat(actualMovieDTO).isEqualTo(expectedMovieDTO);
    }

    @Test
    void shouldUpdateAndReturnMovieDTOObjectWhenUpdateMethodIsCalled() {
        final MovieDTO movieDTOToUpdate = MovieDTO.builder()
                .uuid("123")
                .name("Graf Monte-Cristo")
                .build();
        final Movie dbMovie = Movie.builder()
                .id(1L)
                .uuid("123")
                .build();
        final Movie updatedMovie = Movie.builder()
                .id(1L)
                .uuid("123")
                .name("Graf Monte-Cristo")
                .build();
        final MovieDTO expectedMovieDTO = MovieDTO.builder()
                .uuid("123")
                .name("Graf Monte-Cristo")
                .build();

        Mockito.when(movieRepository.findByUuid(ArgumentMatchers.anyString())).thenReturn(Optional.of(dbMovie));
        Mockito.when(movieRepository.save(ArgumentMatchers.any(Movie.class))).thenReturn(updatedMovie);
        Mockito.when(conversionService.convert(ArgumentMatchers.any(Movie.class), ArgumentMatchers.any()))
                .thenReturn(expectedMovieDTO);

        final MovieDTO actualMovieDTO = movieService.update("123", movieDTOToUpdate);

        Assertions.assertThat(actualMovieDTO).isNotNull();
        Assertions.assertThat(actualMovieDTO).isEqualTo(expectedMovieDTO);
    }

    @Test
    void shouldDeleteMovieEntityWhenDestroysMethodIsCalled() {
        final Movie dbMovie = Movie.builder()
                .id(1L)
                .uuid("123")
                .build();

        Mockito.when(movieRepository.findByUuid(ArgumentMatchers.anyString())).thenReturn(Optional.of(dbMovie));
        Mockito.doNothing()
                .when(movieRepository)
                .delete(ArgumentMatchers.any(Movie.class));

        movieService.destroy("123");
    }

}

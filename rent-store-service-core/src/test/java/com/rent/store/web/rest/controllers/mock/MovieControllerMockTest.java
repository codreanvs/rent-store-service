package com.rent.store.web.rest.controllers.mock;

import com.rent.store.models.dtos.MovieDTO;
import com.rent.store.models.enums.MovieType;
import com.rent.store.persistence.entities.Movie;
import com.rent.store.persistence.repositories.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private JacksonTester<MovieDTO> movieDTOJacksonTester;

    @Autowired
    private JacksonTester<List<MovieDTO>> movieDTOsJacksonTester;

    @Test
    public void shouldReturnListOfMoviesWhenHttpRequestIsMade() throws Exception {
        BDDMockito.given(movieRepository.findAll())
                .willReturn(Collections.singletonList(
                        Movie.builder()
                                .uuid("123")
                                .build()
                        )
                );

        final MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/movies")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(movieDTOsJacksonTester.write(Collections.singletonList(
                        MovieDTO.builder()
                                .uuid("123")
                                .build()
                                )
                        ).getJson()
                );
    }

    @Test
    public void shouldStoreAndReturnMovieWhenHttpRequestIsMade() throws Exception {
        final MovieDTO movieDTOToSave = MovieDTO.builder()
                .movieType(MovieType.REGULAR)
                .name("Spider Man")
                .stockQuantity(10)
                .unitPrice(10)
                .currency("SEK")
                .build();
        final Movie movieToSave = Movie.builder()
                .uuid("123")
                .movieType(MovieType.REGULAR)
                .name("Spider Man")
                .stockQuantity(10)
                .unitPrice(10)
                .currency("SEK")
                .build();
        final Movie savedMovie = Movie.builder()
                .id(1L)
                .uuid("123")
                .movieType(MovieType.REGULAR)
                .name("Spider Man")
                .stockQuantity(10)
                .unitPrice(10)
                .currency("SEK")
                .build();
        final MovieDTO returnedMovieDTO = MovieDTO.builder()
                .uuid("123")
                .movieType(MovieType.REGULAR)
                .name("Spider Man")
                .stockQuantity(10)
                .unitPrice(10)
                .currency("SEK")
                .build();

        BDDMockito.given(movieRepository.save(movieToSave)).willReturn(savedMovie);

        final MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/movies")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(movieDTOJacksonTester.write(movieDTOToSave).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.getContentAsString())
                .isEqualTo(movieDTOJacksonTester.write(returnedMovieDTO).getJson());
    }

    @Test
    public void shouldDeleteMovieWhenHttpRequestIsMade() throws Exception {
        final Movie movieToDelete = Movie.builder()
                .id(11L)
                .uuid("44a384ab-836e-4bdd-9bfd-6e5995128e51")
                .movieType(MovieType.OLD)
                .name("Graf Monte-Cristo")
                .stockQuantity(10)
                .unitPrice(10)
                .currency("SEK")
                .build();

        BDDMockito.willDoNothing()
                .given(movieRepository)
                .delete(movieToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete(
                "/api/v1/movies/{uuid}", "44a384ab-836e-4bdd-9bfd-6e5995128e51"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

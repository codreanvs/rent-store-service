package com.rent.store.web.rest.controllers.httprequests;

import com.rent.store.models.dtos.MovieDTO;
import com.rent.store.models.enums.MovieType;
import com.rent.store.util.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerHttpRequestsTest {

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnListOfMoviesWhenHttpRequestIsMade() {
        final ResponseEntity<List<MovieDTO>> responseEntity = testRestTemplate.exchange(
                String.format(TestConstants.HOST_AND_API_FORMAT, localServerPort) + "/movies",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieDTO>>() {}
        );
        final List<MovieDTO> movieDTOs = responseEntity.getBody();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(movieDTOs).isNotEmpty();
    }

    @Test
    void shouldStoreAndReturnMovieWhenHttpRequestIsMade() {
        final MovieDTO expectedMovieDTO = MovieDTO.builder()
                .movieType(MovieType.OLD)
                .name("Graf Monte-Cristo")
                .stockQuantity(0)
                .unitPrice(10)
                .currency("SEK")
                .build();
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<Object> httpEntity = new HttpEntity<>(expectedMovieDTO, httpHeaders);
        final ResponseEntity<MovieDTO> responseEntity = testRestTemplate.exchange(
                String.format(TestConstants.HOST_AND_API_FORMAT, localServerPort) + "/movies",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<MovieDTO>() {}
        );
        final MovieDTO movieDTO = responseEntity.getBody();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(movieDTO).isNotNull();
        Assertions.assertThat(movieDTO.getUuid()).isNotNull();
        Assertions.assertThat(movieDTO.getMovieType()).isEqualTo(expectedMovieDTO.getMovieType());
        Assertions.assertThat(movieDTO.getName()).isEqualTo(expectedMovieDTO.getName());
        Assertions.assertThat(movieDTO.getStockQuantity()).isEqualTo(expectedMovieDTO.getStockQuantity());
        Assertions.assertThat(movieDTO.getUnitPrice()).isEqualTo(expectedMovieDTO.getUnitPrice());
        Assertions.assertThat(movieDTO.getCurrency()).isEqualTo(expectedMovieDTO.getCurrency());
    }

    @Test
    void shouldReturnMovieWhenHttpRequestIsMade() {
        final MovieDTO expectedMovieDTO = MovieDTO.builder()
                .uuid("0dddfe0c-de96-4282-bb7f-fcb752941163")
                .movieType(MovieType.OLD)
                .name("Graf Monte-Cristo")
                .stockQuantity(10)
                .unitPrice(10)
                .currency("SEK")
                .build();
        final ResponseEntity<MovieDTO> responseEntity = testRestTemplate.exchange(
                String.format(TestConstants.HOST_AND_API_FORMAT, localServerPort)
                        + "/movies/0dddfe0c-de96-4282-bb7f-fcb752941163",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MovieDTO>() {}
        );
        final MovieDTO movieDTO = responseEntity.getBody();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(movieDTO).isNotNull();
        Assertions.assertThat(movieDTO).isEqualTo(expectedMovieDTO);
    }

    @Test
    void shouldUpdateAndReturnMovieWhenHttpRequestIsMade() {
        final MovieDTO movieDTOToUpdate = MovieDTO.builder()
                .uuid("6cd9ba60-907f-4000-9b86-bb1ce358dfdd")
                .movieType(MovieType.OLD)
                .name("Graf Monte-Cristo")
                .stockQuantity(10)
                .unitPrice(15)
                .currency("SEK")
                .build();
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<Object> httpEntity = new HttpEntity<>(movieDTOToUpdate, httpHeaders);
        final ResponseEntity<MovieDTO> responseEntity = testRestTemplate.exchange(
                String.format(TestConstants.HOST_AND_API_FORMAT, localServerPort)
                        + "/movies/6cd9ba60-907f-4000-9b86-bb1ce358dfdd",
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<MovieDTO>() {}
        );
        final MovieDTO movieDTO = responseEntity.getBody();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(movieDTO).isNotNull();
        Assertions.assertThat(movieDTO).isEqualTo(movieDTOToUpdate);
    }

    @Test
    void shouldDeleteMovieWhenHttpRequestIsMade() {
        final ResponseEntity<Object> responseEntity = testRestTemplate.exchange(
                String.format(TestConstants.HOST_AND_API_FORMAT, localServerPort)
                        + "/movies/768c6ed6-a427-4db3-a252-c7c67c72c456",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Object>() {}
        );
        final Object responseEntityBody = responseEntity.getBody();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntityBody).isNull();
    }

}

package com.rent.store.web.rest.controllers;

import com.rent.store.models.dtos.MovieDTO;
import com.rent.store.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAll() {
        return ResponseEntity.ok(movieService.getAll());
    }

    @PostMapping
    public ResponseEntity<MovieDTO> store(@RequestBody final MovieDTO movieDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieService.store(movieDTO));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MovieDTO> get(@PathVariable final String uuid) {
        return ResponseEntity.ok(movieService.get(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<MovieDTO> update(@PathVariable final String uuid, @RequestBody final MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.update(uuid, movieDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(@PathVariable final String uuid) {
        movieService.destroy(uuid);

        return ResponseEntity.noContent().build();
    }

}

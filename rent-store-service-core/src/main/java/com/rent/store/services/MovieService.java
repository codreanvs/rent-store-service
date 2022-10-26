package com.rent.store.services;

import com.rent.store.models.dtos.MovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAll();

    MovieDTO store(MovieDTO movieDTO);

    MovieDTO get(String uuid);

    MovieDTO update(String uuid, MovieDTO movieDTO);

    void destroy(String uuid);

}

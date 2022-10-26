package com.rent.store.services.impl;

import com.rent.store.aspects.annotations.LogErrorAlertAfterThrowingTargetAction;
import com.rent.store.aspects.annotations.LogInfoAlertAfterTargetAction;
import com.rent.store.services.MovieService;
import com.rent.store.exceptions.ResourceNotFoundException;
import com.rent.store.models.dtos.MovieDTO;
import com.rent.store.persistence.entities.Movie;
import com.rent.store.persistence.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private static final String ERROR_MESSAGE = "Movie not found.";

    private final ConversionService conversionService;

    private final MovieRepository movieRepository;

    @LogInfoAlertAfterTargetAction("Fetched list of all movies.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch list of all movies.")
    @Override
    public List<MovieDTO> getAll() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> conversionService.convert(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    @LogInfoAlertAfterTargetAction("Movie has been successfully stored.")
    @LogErrorAlertAfterThrowingTargetAction("Error to store a movie.")
    @Override
    public MovieDTO store(final MovieDTO movieDTO) {
        return conversionService.convert(
                movieRepository.save(
                        Objects.requireNonNull(conversionService.convert(movieDTO, Movie.class))
                                .toBuilder()
                                .uuid(UUID.randomUUID().toString())
                                .build()
                ),
                MovieDTO.class
        );
    }

    @LogInfoAlertAfterTargetAction("Movie has been successfully fetched.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch a movie.")
    @Override
    public MovieDTO get(final String uuid) {
        return conversionService.convert(
                movieRepository.findByUuid(uuid)
                        .orElseThrow(() -> new ResourceNotFoundException(ERROR_MESSAGE)),
                MovieDTO.class
        );
    }

    @LogInfoAlertAfterTargetAction("Movie has been successfully updated.")
    @LogErrorAlertAfterThrowingTargetAction("Error to update a movie.")
    @Override
    public MovieDTO update(final String uuid, final MovieDTO movieDTO) {
        return conversionService.convert(
                movieRepository.save(
                        movieRepository.findByUuid(uuid)
                                .map(movie -> movie.toBuilder()
                                        .movieType(movieDTO.getMovieType())
                                        .name(movieDTO.getName())
                                        .stockQuantity(movieDTO.getStockQuantity())
                                        .unitPrice(movieDTO.getUnitPrice())
                                        .currency(movieDTO.getCurrency())
                                        .build())
                                .orElseThrow(() -> new ResourceNotFoundException("Movie was not saved."))
                ),
                MovieDTO.class
        );
    }

    @LogInfoAlertAfterTargetAction("Movie has been successfully destroyed.")
    @LogErrorAlertAfterThrowingTargetAction("Error to destroy a movie.")
    @Override
    public void destroy(final String uuid) {
        movieRepository.delete(
                movieRepository.findByUuid(uuid)
                        .orElseThrow(() -> new ResourceNotFoundException(ERROR_MESSAGE))
        );
    }

}

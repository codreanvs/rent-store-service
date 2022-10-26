package com.rent.store.conversions;

import com.rent.store.models.dtos.MovieDTO;
import com.rent.store.persistence.entities.Movie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieDTOToMovieConverter implements Converter<MovieDTO, Movie> {

    @Override
    public Movie convert(final MovieDTO source) {
        return Movie.builder()
                .movieType(source.getMovieType())
                .name(source.getName())
                .stockQuantity(source.getStockQuantity())
                .unitPrice(source.getUnitPrice())
                .currency(source.getCurrency())
                .build();
    }

}

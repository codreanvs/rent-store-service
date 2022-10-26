package com.rent.store.conversions;

import com.rent.store.models.dtos.MovieDTO;
import com.rent.store.persistence.entities.Movie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovieToMovieDTOConverter implements Converter<Movie, MovieDTO> {

    @Override
    public MovieDTO convert(final Movie source) {
        return MovieDTO.builder()
                .uuid(source.getUuid())
                .movieType(source.getMovieType())
                .name(source.getName())
                .stockQuantity(source.getStockQuantity())
                .unitPrice(source.getUnitPrice())
                .currency(source.getCurrency())
                .build();
    }

}

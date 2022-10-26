package com.rent.store.configs;

import com.rent.store.conversions.InventoryMovieToInventoryMovieDTOConverter;
import com.rent.store.conversions.MovieDTOToMovieConverter;
import com.rent.store.conversions.MovieToMovieDTOConverter;
import com.rent.store.conversions.RentalToRentalDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
@RequiredArgsConstructor
public class ConversionServiceConfiguration {

    private final MovieDTOToMovieConverter movieDTOToMovieConverter;

    private final MovieToMovieDTOConverter movieToMovieDTOConverter;

    private final RentalToRentalDTOConverter rentalToRentalDTOConverter;

    private final InventoryMovieToInventoryMovieDTOConverter inventoryMovieToInventoryMovieDTOConverter;

    @Bean
    public ConversionService conversionService() {
        final DefaultConversionService conversionService = new DefaultConversionService();

        conversionService.addConverter(movieDTOToMovieConverter);
        conversionService.addConverter(movieToMovieDTOConverter);
        conversionService.addConverter(rentalToRentalDTOConverter);
        conversionService.addConverter(inventoryMovieToInventoryMovieDTOConverter);

        return conversionService;
    }

}

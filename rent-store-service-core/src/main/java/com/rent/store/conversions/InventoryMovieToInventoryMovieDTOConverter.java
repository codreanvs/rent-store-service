package com.rent.store.conversions;

import com.rent.store.models.dtos.InventoryMovieDTO;
import com.rent.store.persistence.entities.InventoryMovie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InventoryMovieToInventoryMovieDTOConverter implements Converter<InventoryMovie, InventoryMovieDTO> {

    @Override
    public InventoryMovieDTO convert(final InventoryMovie source) {
        return InventoryMovieDTO.builder()
                .uuid(source.getUuid())
                .movieType(source.getMovieType())
                .name(source.getName())
                .days(source.getDays())
                .quantity(source.getQuantity())
                .unitPrice(source.getUnitPrice())
                .totalPrice(source.getTotalPrice())
                .currency(source.getCurrency())
                .build();
    }

}

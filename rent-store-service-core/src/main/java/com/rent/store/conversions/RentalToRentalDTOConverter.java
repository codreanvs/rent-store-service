package com.rent.store.conversions;

import com.rent.store.models.dtos.RentalDTO;
import com.rent.store.models.dtos.RentalItemDTO;
import com.rent.store.persistence.entities.Rental;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.stream.Collectors;

@Component
public class RentalToRentalDTOConverter implements Converter<Rental, RentalDTO> {

    @Override
    public RentalDTO convert(final Rental source) {
        final RentalDTO rentalDTO = RentalDTO.builder()
                .uuid(source.getUuid())
                .rentalItems(
                        source.getRentalItems()
                                .stream()
                                .map(rentalItem -> RentalItemDTO.builder()
                                        .rentalUuid(rentalItem.getRental().getUuid())
                                        .movieUuid(rentalItem.getMovie().getUuid())
                                        .quantity(rentalItem.getQuantity())
                                        .unitPrice(rentalItem.getUnitPrice())
                                        .currency(rentalItem.getCurrency())
                                        .startDate(rentalItem.getStartDate())
                                        .endDate(rentalItem.getEndDate())
                                        .status(rentalItem.getStatus())
                                        .build()
                                )
                                .collect(Collectors.toSet())
                )
                .status(source.getStatus())
                .build();

        return rentalDTO.toBuilder()
                .totalPrice(rentalDTO.getRentalItems()
                        .stream()
                        .mapToDouble(rentalItem -> rentalItem.getUnitPrice() *
                                Period.between(
                                        rentalItem.getStartDate().toLocalDate(),
                                        rentalItem.getEndDate().toLocalDate()
                                ).getDays()
                                * rentalItem.getQuantity()
                        ).sum()
                )
                .currency(rentalDTO.getRentalItems().stream().findFirst().get().getCurrency())
                .build();
    }

}

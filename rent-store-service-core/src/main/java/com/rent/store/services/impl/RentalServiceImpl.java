package com.rent.store.services.impl;

import com.rent.store.aspects.annotations.LogErrorAlertAfterThrowingTargetAction;
import com.rent.store.aspects.annotations.LogInfoAlertAfterTargetAction;
import com.rent.store.persistence.repositories.RentalItemRepository;
import com.rent.store.persistence.repositories.RentalRepository;
import com.rent.store.exceptions.ResourceNotFoundException;
import com.rent.store.models.dtos.RentalDTO;
import com.rent.store.models.dtos.RentalItemDTO;
import com.rent.store.models.enums.RentalStatus;
import com.rent.store.persistence.entities.Movie;
import com.rent.store.persistence.entities.Rental;
import com.rent.store.persistence.entities.RentalItem;
import com.rent.store.persistence.repositories.MovieRepository;
import com.rent.store.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final ConversionService conversionService;

    private final MovieRepository movieRepository;

    private final RentalRepository rentalRepository;

    private final RentalItemRepository rentalItemRepository;

    @LogInfoAlertAfterTargetAction("Fetched list of all rentals.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch list of all rentals.")
    @Override
    public List<RentalDTO> getAll() {
        return rentalRepository.findAll()
                .stream()
                .map(rental -> conversionService.convert(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }

    @LogInfoAlertAfterTargetAction("Rental has been successfully fetched to preview.")
    @LogErrorAlertAfterThrowingTargetAction("Error to preview a rental.")
    @Override
    public RentalDTO preview(final RentalDTO rentalDTO) {
        final Set<RentalItemDTO> rentalItems = rentalDTO.getRentalItems()
                .stream()
                .peek(rentalItem -> {
                    final Movie movie = movieRepository.findByUuid(rentalItem.getMovieUuid())
                            .orElseThrow(() -> new ResourceNotFoundException("Movie not found."));
                    rentalItem.setUnitPrice(movie.getUnitPrice());
                    rentalItem.setCurrency(movie.getCurrency());
                })
                .collect(Collectors.toSet());

        return RentalDTO.builder()
                .rentalItems(rentalItems)
                .totalPrice(rentalItems
                        .stream()
                        .mapToDouble(rentalItem ->
                                rentalItem.getUnitPrice() *
                                        Period.between(
                                                rentalItem.getStartDate().toLocalDate(),
                                                rentalItem.getEndDate().toLocalDate()
                                        ).getDays()
                                        * rentalItem.getQuantity()
                        )
                        .sum()
                )
                .currency(rentalItems.stream().findFirst().get().getCurrency())
                .build();
    }

    @LogInfoAlertAfterTargetAction("Rental has been successfully stored.")
    @LogErrorAlertAfterThrowingTargetAction("Error to store a rental.")
    @Override
    @Transactional
    public RentalDTO store(final RentalDTO rentalDTO) {
        final Rental rental = rentalRepository.save(
                Rental.builder()
                        .uuid(UUID.randomUUID().toString())
                        .status(RentalStatus.RENT_INIT)
                        .build()
        );
        rentalItemRepository.saveAll(
                rentalDTO.getRentalItems()
                        .stream()
                        .map(rentalItemDTO -> {
                            final Movie movie = movieRepository.findByUuid(rentalItemDTO.getMovieUuid())
                                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found."));
                            movieRepository.decrementStockQuantity(movie.getUuid(), rentalItemDTO.getQuantity());

                            return RentalItem.builder()
                                    .rental(rental)
                                    .movie(movie)
                                    .quantity(rentalItemDTO.getQuantity())
                                    .unitPrice(movie.getUnitPrice())
                                    .currency(movie.getCurrency())
                                    .startDate(rentalItemDTO.getStartDate())
                                    .endDate(rentalItemDTO.getEndDate())
                                    .status(RentalStatus.RENT_INIT)
                                    .build();
                        })
                        .collect(Collectors.toSet())
        );

        return buildRentalDTO(rentalDTO, rental);
    }

    private RentalDTO buildRentalDTO(final RentalDTO rentalDTO, final Rental rental) {
        final RentalDTO rentalDTOResponse = rentalRepository.findByUuid(rental.getUuid())
                .map(dbRental -> RentalDTO.builder()
                        .uuid(dbRental.getUuid())
                        .rentalItems(rentalDTO.getRentalItems()
                                .stream()
                                .map(rentalItem -> {
                                    final Movie movie = movieRepository.findByUuid(rentalItem.getMovieUuid())
                                            .orElseThrow(() -> new ResourceNotFoundException("Movie not found."));

                                    return RentalItemDTO.builder()
                                            .rentalUuid(rentalItem.getRentalUuid())
                                            .movieUuid(rentalItem.getMovieUuid())
                                            .quantity(rentalItem.getQuantity())
                                            .unitPrice(movie.getUnitPrice())
                                            .currency(movie.getCurrency())
                                            .startDate(rentalItem.getStartDate())
                                            .endDate(rentalItem.getEndDate())
                                            .status(rentalItem.getStatus())
                                            .build();
                                })
                                .collect(Collectors.toSet())
                        )
                        .status(dbRental.getStatus())
                        .build()
                )
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found."));

        return rentalDTOResponse.toBuilder()
                .totalPrice(rentalDTOResponse.getRentalItems()
                        .stream()
                        .mapToDouble(rentalItem -> rentalItem.getUnitPrice() *
                                Period.between(
                                        rentalItem.getStartDate().toLocalDate(),
                                        rentalItem.getEndDate().toLocalDate()
                                ).getDays()
                                * rentalItem.getQuantity()
                        ).sum()
                )
                .currency(rentalDTOResponse.getRentalItems().stream().findFirst().get().getCurrency())
                .build();
    }

    @LogInfoAlertAfterTargetAction("Rental has been successfully fetched.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch a rental.")
    @Override
    public RentalDTO get(final String uuid) {
        return conversionService.convert(
                rentalRepository.findByUuid(uuid)
                        .orElseThrow(() -> new ResourceNotFoundException("Rental not found.")),
                RentalDTO.class
        );
    }

    @LogErrorAlertAfterThrowingTargetAction("Error to update a rental. There is not the implementation yet.")
    @Override
    public RentalDTO update(final String uuid, final RentalDTO rentalDTO) {
        throw new NotImplementedException();
    }

    @LogInfoAlertAfterTargetAction("Rental has been successfully returned.")
    @LogErrorAlertAfterThrowingTargetAction("Error to return a rental.")
    @Override
    @Transactional
    public RentalDTO markAsReturned(final String uuid, final RentalDTO rentalDTO) {
        final Rental rental = rentalRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Rental was not saved."));
        final List<String> requestMovieUuids = rentalDTO.getRentalItems()
                .stream()
                .map(RentalItemDTO::getMovieUuid)
                .collect(Collectors.toList());
        final int requestMovieQuantities = rentalDTO.getRentalItems()
                .stream()
                .mapToInt(RentalItemDTO::getQuantity)
                .sum();
        rental.setRentalItems(
                rental.getRentalItems()
                        .stream()
                        .filter(rentalItem -> requestMovieUuids.contains(rentalItem.getMovie().getUuid()))
                        .peek(rentalItem -> {
                            final int quantity = rentalDTO.getRentalItems()
                                    .stream()
                                    .filter(childRentalItem -> childRentalItem.getMovieUuid().equals(rentalItem.getMovie().getUuid()))
                                    .findFirst()
                                    .orElseThrow(() -> new ResourceNotFoundException("RentalItem was not saved."))
                                    .getQuantity();
                            rentalItem.setStatus(RentalStatus.RETURNED);
                            rentalItem.setQuantity(quantity);
                            movieRepository.incrementStockQuantity(rentalItem.getMovie().getUuid(), quantity);
                        })
                        .collect(Collectors.toSet())
        );
        final boolean areAllMoviesReturnedByUuids = new HashSet<>(
                rental.getRentalItems()
                        .stream()
                        .map(rentalItem -> rentalItem.getMovie().getUuid())
                        .collect(Collectors.toList())
        ).containsAll(requestMovieUuids);
        final boolean areAllMoviesReturnedByQuantities = rental.getRentalItems()
                .stream()
                .mapToInt(RentalItem::getQuantity)
                .sum() == requestMovieQuantities;
        if (areAllMoviesReturnedByUuids && areAllMoviesReturnedByQuantities) {
            rental.setStatus(RentalStatus.RETURNED);
        }

        return conversionService.convert(rentalRepository.save(rental), RentalDTO.class);
    }

    @LogInfoAlertAfterTargetAction("Rental has been successfully destroyed.")
    @LogErrorAlertAfterThrowingTargetAction("Error to destroy a rental.")
    @Override
    public void destroy(final String uuid) {
        rentalRepository.delete(
                rentalRepository.findByUuid(uuid)
                        .orElseThrow(() -> new ResourceNotFoundException("Rental not found."))
        );
    }

}

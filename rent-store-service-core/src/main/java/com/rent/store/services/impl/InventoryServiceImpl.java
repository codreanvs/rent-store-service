package com.rent.store.services.impl;

import com.rent.store.aspects.annotations.LogErrorAlertAfterThrowingTargetAction;
import com.rent.store.aspects.annotations.LogInfoAlertAfterTargetAction;
import com.rent.store.models.dtos.InventoryDTO;
import com.rent.store.services.InventoryMovieService;
import com.rent.store.services.InventoryService;
import com.rent.store.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final Clock clock;

    private final MovieService movieService;

    private final InventoryMovieService inventoryMovieService;

    @LogInfoAlertAfterTargetAction("Fetched list of all inventory movies.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch list of all inventory movies.")
    @Override
    public InventoryDTO getInventoryResult(final InventoryDTO inventoryDTO) {
        if (Objects.isNull(inventoryDTO.getDate())) {
            return InventoryDTO.builder()
                    .stockMovies(movieService.getAll())
                    .rentedMovies(inventoryMovieService.getAllRentedItems())
                    .rentedNotReturnedMovies(
                            inventoryMovieService.getAllNotReturnedRentedItems(LocalDateTime.now(clock))
                    )
                    .build();
        }

        return InventoryDTO.builder()
                .stockMovies(movieService.getAll())
                .rentedMovies(inventoryMovieService.getAllRentedItems(inventoryDTO.getDate()))
                .rentedNotReturnedMovies(
                        inventoryMovieService.getAllNotReturnedRentedItems(inventoryDTO.getDate())
                )
                .build();
    }

}

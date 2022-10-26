package com.rent.store.services.impl;

import com.rent.store.aspects.annotations.LogErrorAlertAfterThrowingTargetAction;
import com.rent.store.aspects.annotations.LogInfoAlertAfterTargetAction;
import com.rent.store.models.dtos.InventoryMovieDTO;
import com.rent.store.persistence.repositories.InventoryMovieRepository;
import com.rent.store.services.InventoryMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryMovieServiceImpl implements InventoryMovieService {

    private final ConversionService conversionService;

    private final InventoryMovieRepository inventoryMovieRepository;

    @LogInfoAlertAfterTargetAction("Fetched list of all rented movies.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch list of all rented movies.")
    @Override
    public List<InventoryMovieDTO> getAllRentedItems() {
        return inventoryMovieRepository.findAll()
                .stream()
                .map(inventoryMovie -> conversionService.convert(inventoryMovie, InventoryMovieDTO.class))
                .collect(Collectors.toList());
    }

    @LogInfoAlertAfterTargetAction("Fetched list of all rented movies by a date.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch list of all rented movies by a date.")
    @Override
    public List<InventoryMovieDTO> getAllRentedItems(final LocalDateTime date) {
        return inventoryMovieRepository.findAllRentedItems(date)
                .stream()
                .map(inventoryMovie -> conversionService.convert(inventoryMovie, InventoryMovieDTO.class))
                .collect(Collectors.toList());
    }

    @LogInfoAlertAfterTargetAction("Fetched list of all not returned rented movies by a date.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch list of all not returned rented movies by a date.")
    @Override
    public List<InventoryMovieDTO> getAllNotReturnedRentedItems(final LocalDateTime date) {
        return inventoryMovieRepository.findAllNotReturnedRentedItems(date)
                .stream()
                .map(inventoryMovie -> conversionService.convert(inventoryMovie, InventoryMovieDTO.class))
                .collect(Collectors.toList());
    }

}

package com.rent.store.services;

import com.rent.store.models.dtos.InventoryMovieDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryMovieService {

    List<InventoryMovieDTO> getAllRentedItems();

    List<InventoryMovieDTO> getAllRentedItems(LocalDateTime date);

    List<InventoryMovieDTO> getAllNotReturnedRentedItems(LocalDateTime date);

}

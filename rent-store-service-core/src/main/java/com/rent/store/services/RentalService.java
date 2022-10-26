package com.rent.store.services;

import com.rent.store.models.dtos.RentalDTO;

import java.util.List;

public interface RentalService {

    List<RentalDTO> getAll();

    RentalDTO preview(RentalDTO rentalDTO);

    RentalDTO store(RentalDTO rentalDTO);

    RentalDTO get(String uuid);

    RentalDTO update(String uuid, RentalDTO rentalDTO);

    RentalDTO markAsReturned(String uuid, RentalDTO rentalDTO);

    void destroy(String uuid);

}

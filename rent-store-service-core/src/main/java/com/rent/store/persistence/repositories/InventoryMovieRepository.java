package com.rent.store.persistence.repositories;

import com.rent.store.persistence.entities.InventoryMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryMovieRepository extends JpaRepository<InventoryMovie, String> {

    @Query("SELECT i FROM InventoryMovie i" +
            " WHERE i.startDate < :date" +
            " AND i.endDate > :date"
    )
    List<InventoryMovie> findAllRentedItems(LocalDateTime date);

    @Query("SELECT i FROM InventoryMovie i" +
            " WHERE i.endDate < :date"
    )
    List<InventoryMovie> findAllNotReturnedRentedItems(LocalDateTime date);

}

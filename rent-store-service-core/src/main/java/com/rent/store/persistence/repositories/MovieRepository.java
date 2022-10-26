package com.rent.store.persistence.repositories;

import com.rent.store.persistence.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByUuid(String uuid);

    @Modifying
    @Query(value = "UPDATE RENT_OWNER.MOVIES" +
            " SET STOCK_QUANTITY = (STOCK_QUANTITY + :quantity)" +
            " WHERE UUID = :uuid", nativeQuery = true
    )
    void incrementStockQuantity(@Param("uuid") String uuid, @Param("quantity") int quantity);

    @Modifying
    @Query(value = "UPDATE RENT_OWNER.MOVIES m" +
            " SET m.STOCK_QUANTITY = (m.STOCK_QUANTITY - :quantity)" +
            " WHERE m.UUID = :uuid", nativeQuery = true
    )
    void decrementStockQuantity(@Param("uuid") String uuid, @Param("quantity") int quantity);

}

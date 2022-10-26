package com.rent.store.persistence.entities;

import com.rent.store.models.enums.MovieType;
import com.rent.store.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "INVENTORY_MOVIES", schema = Constants.APP_DATABASE_SCHEMA)
public class InventoryMovie {

    @Id
    private String uuid;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private MovieType movieType;

    private String name;

    private int days;

    private int quantity;

    private double unitPrice;

    private double totalPrice;

    private String currency;

}

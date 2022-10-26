package com.rent.store.models.dtos;

import com.rent.store.models.enums.MovieType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryMovieDTO {

    private String uuid;

    private MovieType movieType;

    private String name;

    private int days;

    private int quantity;

    private double unitPrice;

    private double totalPrice;

    private String currency;

}

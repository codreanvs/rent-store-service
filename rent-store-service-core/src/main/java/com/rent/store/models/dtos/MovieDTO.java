package com.rent.store.models.dtos;

import com.rent.store.models.enums.MovieType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MovieDTO {

    private String uuid;

    private MovieType movieType;

    private String name;

    private int stockQuantity;

    private double unitPrice;

    private String currency;

}

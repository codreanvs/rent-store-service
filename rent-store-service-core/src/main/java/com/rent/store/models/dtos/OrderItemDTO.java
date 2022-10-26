package com.rent.store.models.dtos;

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
public class OrderItemDTO {

    private String movieUuid;

    private int quantity;

    private double unitPrice;

    private double price;

    private String currency;

}

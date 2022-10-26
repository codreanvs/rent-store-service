package com.rent.store.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String orderUuid;

    private String rentalUuid;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<OrderItemDTO> orderItems;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private double totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;

}

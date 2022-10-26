package com.rent.store.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rent.store.models.enums.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RentalDTO {

    private String uuid;

    private Set<RentalItemDTO> rentalItems;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private double totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

}

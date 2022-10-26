package com.rent.store.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rent.store.models.enums.TransactionStatus;

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
public class TransactionDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uuid;

    private OrderDTO order;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TransactionStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

}

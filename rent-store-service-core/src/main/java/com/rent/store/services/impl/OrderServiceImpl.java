package com.rent.store.services.impl;

import com.rent.store.aspects.annotations.LogErrorAlertAfterThrowingTargetAction;
import com.rent.store.aspects.annotations.LogInfoAlertAfterTargetAction;
import com.rent.store.exceptions.ResourceNotFoundException;
import com.rent.store.models.dtos.OrderDTO;
import com.rent.store.models.dtos.OrderItemDTO;
import com.rent.store.models.dtos.RentalDTO;
import com.rent.store.models.dtos.RentalItemDTO;
import com.rent.store.persistence.entities.Order;
import com.rent.store.persistence.repositories.OrderRepository;
import com.rent.store.persistence.repositories.RentalRepository;
import com.rent.store.services.OrderService;
import com.rent.store.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RentalService rentalService;

    private final RentalRepository rentalRepository;

    @LogInfoAlertAfterTargetAction("Order has been successfully stored.")
    @LogErrorAlertAfterThrowingTargetAction("Error to store an order.")
    @Override
    public OrderDTO store(final OrderDTO orderDTO) {
        final RentalDTO rentalDTO = rentalService.get(orderDTO.getRentalUuid());
        final RentalItemDTO rentalItemDTO = rentalDTO.getRentalItems().stream()
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("RentalItem not found."));
        final Set<OrderItemDTO> orderItemDTOs = rentalDTO.getRentalItems()
                .stream()
                .map(iRentalItemDTO -> {
                    final int nDays = rentalDTO.getRentalItems()
                            .stream()
                            .flatMapToInt(uRentalItemDTO ->
                                    IntStream.of(Period.between(
                                            uRentalItemDTO.getStartDate().toLocalDate(),
                                            uRentalItemDTO.getEndDate().toLocalDate()
                                    ).getDays())
                            ).sum();

                    return OrderItemDTO.builder()
                            .movieUuid(iRentalItemDTO.getMovieUuid())
                            .quantity(iRentalItemDTO.getQuantity())
                            .unitPrice(iRentalItemDTO.getUnitPrice())
                            .price(iRentalItemDTO.getUnitPrice() * nDays)
                            .currency(iRentalItemDTO.getCurrency())
                            .build();
                }).collect(Collectors.toSet());
        final Order order = orderRepository.save(
                Order.builder()
                        .rental(rentalRepository.findByUuid(rentalDTO.getUuid())
                                .orElseThrow(() -> new ResourceNotFoundException("Rental not found."))
                        )
                        .uuid(UUID.randomUUID().toString())
                        .totalPrice(orderItemDTOs.stream()
                                .flatMapToDouble(iRentalItemDTO ->
                                        DoubleStream.of(iRentalItemDTO.getPrice() * iRentalItemDTO.getQuantity())
                                ).sum()
                        )
                        .currency(rentalItemDTO.getCurrency())
                        .build()
        );

        return OrderDTO.builder()
                .orderUuid(order.getUuid())
                .rentalUuid(order.getRental().getUuid())
                .orderItems(orderItemDTOs)
                .totalPrice(order.getTotalPrice())
                .currency(order.getCurrency())
                .build();
    }

    @LogInfoAlertAfterTargetAction("Order has been successfully fetched.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch an order.")
    @Override
    public OrderDTO get(final String uuid) {
        final Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        return OrderDTO.builder()
                .orderUuid(order.getUuid())
                .rentalUuid(order.getRental().getUuid())
                .orderItems(order.getOrderItems()
                        .stream()
                        .map(orderItem -> OrderItemDTO.builder()
                                .movieUuid(orderItem.getMovie().getUuid())
                                .quantity(orderItem.getQuantity())
                                .unitPrice(orderItem.getUnitPrice())
                                .currency(order.getCurrency())
                                .build()
                        )
                        .collect(Collectors.toSet())
                )
                .totalPrice(order.getTotalPrice())
                .currency(order.getCurrency())
                .build();
    }

}

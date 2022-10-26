package com.rent.store.services.impl;

import com.rent.store.aspects.annotations.LogErrorAlertAfterThrowingTargetAction;
import com.rent.store.aspects.annotations.LogInfoAlertAfterTargetAction;
import com.rent.store.models.enums.TransactionStatus;
import com.rent.store.persistence.entities.Movie;
import com.rent.store.persistence.entities.Order;
import com.rent.store.persistence.entities.OrderItem;
import com.rent.store.persistence.entities.Transaction;
import com.rent.store.persistence.repositories.TransactionRepository;
import com.rent.store.exceptions.ResourceNotFoundException;
import com.rent.store.models.dtos.OrderDTO;
import com.rent.store.models.dtos.OrderItemDTO;
import com.rent.store.models.dtos.RentalDTO;
import com.rent.store.models.dtos.RentalItemDTO;
import com.rent.store.models.dtos.TransactionDTO;
import com.rent.store.persistence.repositories.MovieRepository;
import com.rent.store.persistence.repositories.OrderItemRepository;
import com.rent.store.persistence.repositories.OrderRepository;
import com.rent.store.services.OrderService;
import com.rent.store.services.RentalService;
import com.rent.store.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final ConversionService conversionService;

    private final RentalService rentalService;

    private final OrderService orderService;

    private final MovieRepository movieRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final TransactionRepository transactionRepository;

    @LogInfoAlertAfterTargetAction("Fetched list of all transactions.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch list of all transactions.")
    @Override
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> {
                    final Order order = transaction.getOrder();

                    return TransactionDTO.builder()
                            .uuid(transaction.getUuid())
                            .order(OrderDTO.builder()
                                    .orderUuid(order.getUuid())
                                    .rentalUuid(order.getRental().getUuid())
                                    .orderItems(order.getOrderItems()
                                            .stream()
                                            .map(orderItem -> OrderItemDTO.builder()
                                                    .movieUuid(orderItem.getMovie().getUuid())
                                                    .quantity(orderItem.getQuantity())
                                                    .unitPrice(orderItem.getUnitPrice())
                                                    .currency(orderItem.getCurrency())
                                                    .build())
                                            .collect(Collectors.toSet())
                                    )
                                    .totalPrice(order.getTotalPrice())
                                    .currency(order.getCurrency())
                                    .build()

                            )
                            .status(transaction.getStatus())
                            .description(transaction.getDescription())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @LogInfoAlertAfterTargetAction("Transaction has been successfully fetched to preview.")
    @LogErrorAlertAfterThrowingTargetAction("Error to preview a transaction.")
    @Override
    public TransactionDTO preview(final TransactionDTO transactionDTO) {
        final OrderDTO orderDTO = transactionDTO.getOrder();
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

        return TransactionDTO.builder()
                .order(OrderDTO.builder()
                        .rentalUuid(rentalDTO.getUuid())
                        .orderItems(orderItemDTOs)
                        .totalPrice(orderItemDTOs.stream()
                                .flatMapToDouble(iRentalItemDTO ->
                                        DoubleStream.of(iRentalItemDTO.getPrice() * iRentalItemDTO.getQuantity())
                                ).sum()
                        )
                        .currency(rentalItemDTO.getCurrency())
                        .build()
                )
                .build();
    }

    @LogInfoAlertAfterTargetAction("Transaction has been successfully stored.")
    @LogErrorAlertAfterThrowingTargetAction("Error to store a transaction.")
    @Override
    public TransactionDTO store(final TransactionDTO transactionDTO) {
        final OrderDTO orderDTO = orderService.store(transactionDTO.getOrder());
        final Order order = orderRepository.findByUuid(orderDTO.getOrderUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        final RentalDTO rentalDTO = rentalService.get(orderDTO.getRentalUuid());
        final int nDays = rentalDTO.getRentalItems()
                .stream()
                .flatMapToInt(uRentalItemDTO ->
                        IntStream.of(Period.between(
                                uRentalItemDTO.getStartDate().toLocalDate(),
                                uRentalItemDTO.getEndDate().toLocalDate()
                        ).getDays())
                ).sum();
        orderItemRepository.saveAll(
                rentalDTO.getRentalItems()
                        .stream()
                        .map(orderItemDTO -> {
                            final Movie movie = movieRepository.findByUuid(orderItemDTO.getMovieUuid())
                                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found."));

                            return OrderItem.builder()
                                    .order(order)
                                    .movie(movie)
                                    .quantity(orderItemDTO.getQuantity())
                                    .unitPrice(orderItemDTO.getUnitPrice() * nDays)
                                    .currency(order.getCurrency())
                                    .build();
                        })
                        .collect(Collectors.toSet())
        );
        final Transaction transaction = transactionRepository.save(
                Transaction.builder()
                        .uuid(UUID.randomUUID().toString())
                        .order(order)
                        .status(TransactionStatus.SUCCESS)
                        .description(transactionDTO.getDescription())
                        .build()
        );

        return TransactionDTO.builder()
                .uuid(transaction.getUuid())
                .order(orderDTO)
                .status(transaction.getStatus())
                .description(transaction.getDescription())
                .build();
    }

    @LogInfoAlertAfterTargetAction("Transaction has been successfully fetched.")
    @LogErrorAlertAfterThrowingTargetAction("Error to fetch a transaction.")
    @Override
    public TransactionDTO get(final String uuid) {
        return conversionService.convert(
                transactionRepository.findByUuid(uuid)
                        .orElseThrow(() -> new ResourceNotFoundException("Rental not found.")),
                TransactionDTO.class
        );
    }

}

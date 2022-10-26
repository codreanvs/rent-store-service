package com.rent.store.services;

import com.rent.store.models.dtos.TransactionDTO;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getAll();

    TransactionDTO preview(TransactionDTO transactionDTO);

    TransactionDTO store(TransactionDTO transactionDTO);

    TransactionDTO get(String uuid);

}

package com.rent.store.web.rest.controllers;

import com.rent.store.models.dtos.TransactionDTO;
import com.rent.store.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @PostMapping("/preview")
    public ResponseEntity<TransactionDTO> preview(@RequestBody final TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.preview(transactionDTO));
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> add(@RequestBody final TransactionDTO transactionDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.store(transactionDTO));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TransactionDTO> get(@PathVariable final String uuid) {
        return ResponseEntity.ok(transactionService.get(uuid));
    }

}

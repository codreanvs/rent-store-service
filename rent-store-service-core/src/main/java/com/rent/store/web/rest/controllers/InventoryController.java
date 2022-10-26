package com.rent.store.web.rest.controllers;

import com.rent.store.models.dtos.InventoryDTO;
import com.rent.store.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDTO> inventory(@RequestBody final InventoryDTO inventoryDTO) {
        return ResponseEntity.ok(inventoryService.getInventoryResult(inventoryDTO));
    }

}

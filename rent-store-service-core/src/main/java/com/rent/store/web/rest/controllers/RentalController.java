package com.rent.store.web.rest.controllers;

import com.rent.store.models.dtos.RentalDTO;
import com.rent.store.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<RentalDTO>> getAll() {
        return ResponseEntity.ok(rentalService.getAll());
    }

    @PostMapping("/preview")
    public ResponseEntity<RentalDTO> preview(@RequestBody final RentalDTO rentalDTO) {
        return ResponseEntity.ok(rentalService.preview(rentalDTO));
    }

    @PostMapping
    public ResponseEntity<RentalDTO> store(@RequestBody final RentalDTO rentalDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rentalService.store(rentalDTO));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RentalDTO> get(@PathVariable final String uuid) {
        return ResponseEntity.ok(rentalService.get(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RentalDTO> update(@PathVariable final String uuid, @RequestBody final RentalDTO rentalDTO) {
        return ResponseEntity.ok(rentalService.update(uuid, rentalDTO));
    }

    @PostMapping("/{uuid}/return")
    public ResponseEntity<RentalDTO> doReturn(@PathVariable final String uuid, @RequestBody final RentalDTO rentalDTO) {
        return ResponseEntity.ok(rentalService.markAsReturned(uuid, rentalDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(@PathVariable final String uuid) {
        rentalService.destroy(uuid);

        return ResponseEntity.noContent().build();
    }

}

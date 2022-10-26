package com.rent.store.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.store.models.enums.RentalStatus;
import com.rent.store.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RENTAL_ITEMS", schema = Constants.APP_DATABASE_SCHEMA)
public class RentalItem {

    @Id
    @GeneratedValue(generator = "RENTAL_ITEMS_ID_SEQUENCE")
    @SequenceGenerator(name = "RENTAL_ITEMS_ID_SEQUENCE",
            sequenceName = "RENT_OWNER.RENTAL_ITEMS_ID_SEQUENCE",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RENTAL_ID", referencedColumnName = "ID")
    private Rental rental;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID")
    private Movie movie;

    private int quantity;

    private double unitPrice;

    private String currency;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

}

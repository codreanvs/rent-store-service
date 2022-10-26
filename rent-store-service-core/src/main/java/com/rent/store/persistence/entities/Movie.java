package com.rent.store.persistence.entities;

import com.rent.store.models.enums.MovieType;
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MOVIES", schema = Constants.APP_DATABASE_SCHEMA)
public class Movie {

    @Id
    @GeneratedValue(generator = "MOVIES_ID_SEQUENCE")
    @SequenceGenerator(name = "MOVIES_ID_SEQUENCE",
            sequenceName = "RENT_OWNER.MOVIES_ID_SEQUENCE",
            allocationSize = 1
    )
    private Long id;

    private String uuid;

    @Enumerated(EnumType.STRING)
    private MovieType movieType;

    private String name;

    private int stockQuantity;

    private double unitPrice;

    private String currency;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RentalItem> rentalItems;

}

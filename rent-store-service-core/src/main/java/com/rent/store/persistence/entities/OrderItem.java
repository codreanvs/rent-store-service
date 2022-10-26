package com.rent.store.persistence.entities;

import com.rent.store.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ORDER_ITEMS", schema = Constants.APP_DATABASE_SCHEMA)
public class OrderItem {

    @Id
    @GeneratedValue(generator = "ORDER_ITEMS_ID_SEQUENCE")
    @SequenceGenerator(name = "ORDER_ITEMS_ID_SEQUENCE",
            sequenceName = "RENT_OWNER.ORDER_ITEMS_ID_SEQUENCE",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
    private Order order;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID")
    private Movie movie;

    private int quantity;

    private double unitPrice;

    private String currency;

}

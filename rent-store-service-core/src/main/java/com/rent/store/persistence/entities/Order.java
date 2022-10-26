package com.rent.store.persistence.entities;

import com.rent.store.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ORDERS", schema = Constants.APP_DATABASE_SCHEMA)
public class Order {

    @Id
    @GeneratedValue(generator = "ORDERS_ID_SEQUENCE")
    @SequenceGenerator(name = "ORDERS_ID_SEQUENCE",
            sequenceName = "RENT_OWNER.ORDERS_ID_SEQUENCE",
            allocationSize = 1
    )
    private Long id;

    private String uuid;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "RENTAL_ID", referencedColumnName = "ID")
    private Rental rental;

    private double totalPrice;

    private String currency;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems;

}

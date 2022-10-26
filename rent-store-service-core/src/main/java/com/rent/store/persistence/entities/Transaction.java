package com.rent.store.persistence.entities;

import com.rent.store.models.enums.TransactionStatus;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TRANSACTIONS", schema = Constants.APP_DATABASE_SCHEMA)
public class Transaction {

    @Id
    @GeneratedValue(generator = "TRANSACTIONS_ID_SEQUENCE")
    @SequenceGenerator(name = "TRANSACTIONS_ID_SEQUENCE",
            sequenceName = "RENT_OWNER.TRANSACTIONS_ID_SEQUENCE",
            allocationSize = 1
    )
    private Long id;

    private String uuid;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "ID")
    private Order order;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String description;

}

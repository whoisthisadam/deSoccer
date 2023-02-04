package com.kasperovich.desoccer.models;

import com.kasperovich.desoccer.enums.PaymentProviders;
import com.kasperovich.desoccer.enums.PaymentStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "payment_details")
@EqualsAndHashCode(exclude = "order")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    Order order;

    @Column(length = 10, precision = 2)
    Long amount;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    PaymentProviders provider;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date"))
    })
    Edit editData;
}
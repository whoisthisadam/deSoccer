package com.kasperovich.desoccer.models;

import com.kasperovich.desoccer.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {
        "user","products"
})
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_details")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "total")
//    @Positive
    Long total;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Payment payment;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date"))
    })
    Edit editData;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Column(name = "is_deleted")
    Boolean isDeleted=false;

    @ManyToMany
    @JoinTable(name = "l_orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    Set<Product>products;



}

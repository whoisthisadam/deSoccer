package com.kasperovich.desoccer.models;


import com.kasperovich.desoccer.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    String name;

    @Column(name = "brand")
    String brand;

    @Column(name = "price")
    Long price;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date"))
    })
    Edit editData;

    @Column(name = "is_deleted")
    Boolean isDeleted=false;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    Discount productDiscount;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Order> orders;

}

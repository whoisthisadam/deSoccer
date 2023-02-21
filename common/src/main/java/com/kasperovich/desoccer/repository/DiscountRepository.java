package com.kasperovich.desoccer.repository;

import com.kasperovich.desoccer.models.Discount;
import com.kasperovich.desoccer.enums.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Discount findDiscountsByName(Discounts discount);

}

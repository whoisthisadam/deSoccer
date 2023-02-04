package com.kasperovich.desoccer.service.discount;

import com.kasperovich.desoccer.models.Discount;

import java.util.List;

public interface DiscountService {

    public List<Discount> findAll();

    public Discount createDiscount(Discount discount);

    public Discount deleteDiscount(Long id);


}

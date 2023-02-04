package com.kasperovich.desoccer.mapping.converters.order;

import com.kasperovich.desoccer.dto.order.OrderGetDto;
import com.kasperovich.desoccer.mapping.mappers.PaymentMapper;
import com.kasperovich.desoccer.mapping.mappers.ProductListMapper;
import com.kasperovich.desoccer.models.Order;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderGetConverter implements Converter<Order, OrderGetDto> {

    PaymentMapper paymentMapper;

    ProductListMapper productListMapper;


    @Override
    public OrderGetDto convert(Order order) {
        OrderGetDto orderGetDto=new OrderGetDto();
        orderGetDto.setId(order.getId());
        orderGetDto.setUserId(order.getUser().getId());
        orderGetDto.setPayment(paymentMapper.toDto(order.getPayment()));
        orderGetDto.setTotal(order.getTotal());
        orderGetDto.setStatus(order.getOrderStatus());
        orderGetDto.setProducts(productListMapper.toDto(new ArrayList<>(order.getProducts())));
        return orderGetDto;
    }
}

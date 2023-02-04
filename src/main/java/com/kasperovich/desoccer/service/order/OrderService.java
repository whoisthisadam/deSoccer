package com.kasperovich.desoccer.service.order;

import com.kasperovich.desoccer.exception.NotDeletableStatusException;
import com.kasperovich.desoccer.models.Order;

import java.util.List;


public interface OrderService {

    public List<Order> findAll();

    public Order createOrder(Order order);

    public Order updateOrder(Order order);

    public Order deleteOrder(Long id) throws NotDeletableStatusException;

}

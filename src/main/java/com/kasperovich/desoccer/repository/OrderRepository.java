package com.kasperovich.desoccer.repository;

import com.kasperovich.desoccer.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

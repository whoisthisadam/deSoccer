package com.kasperovich.desoccer.repository;

import com.kasperovich.desoccer.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

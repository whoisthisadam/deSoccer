package com.kasperovich.desoccer.repository;

import com.kasperovich.desoccer.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

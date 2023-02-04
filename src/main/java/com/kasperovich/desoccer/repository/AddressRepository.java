package com.kasperovich.desoccer.repository;

import com.kasperovich.desoccer.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

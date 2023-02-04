package com.kasperovich.desoccer.repository;

import com.kasperovich.desoccer.models.Discount;
import com.kasperovich.desoccer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findUserByCredentialsLogin(String login);

    public Optional<User> findUserByEmail(String email);

    public List<User>findUsersByUserDiscount(Discount discount);

}

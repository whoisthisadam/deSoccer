package com.kasperovich.desoccer.service.user;

import com.kasperovich.desoccer.exception.BadPasswordException;
import com.kasperovich.desoccer.models.User;

import java.util.List;

public interface UserService {

    public User createUser(User user) throws BadPasswordException;

    public List<User> findAll();

    public User updateUser(User user);

    public User deleteUser(Long id);

}

package com.training.bank_system.service;

import com.training.bank_system.modelos.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUser(Long user_id);

    List<User> getAllUsers();

    User editUser(Long id, User userToEdit);

    boolean deleteUser(Long id);

}

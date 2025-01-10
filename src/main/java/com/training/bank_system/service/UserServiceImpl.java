package com.training.bank_system.service;

import com.training.bank_system.modelos.User;
import com.training.bank_system.repositorio.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(Long user_id) {
        return userRepository.findById(user_id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User editUser(Long id, User userToEdit) {
        User findUser = userRepository.findById(id).get();
        findUser.setName(userToEdit.getName());
        findUser.setEmail(userToEdit.getEmail());
        return userRepository.save(findUser);
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}

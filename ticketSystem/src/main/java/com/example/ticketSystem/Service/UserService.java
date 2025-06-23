package com.example.ticketSystem.Service;

import com.example.ticketSystem.Repository.UserRepository;
import com.example.ticketSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User findByName(String name) {        // ← new method
        return userRepository.findByName(name);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        Iterable<User> usersIterable = userRepository.findAll();
        List<User> usersList = new ArrayList<>();
        usersIterable.forEach(usersList::add);
        return usersList;
    }
}

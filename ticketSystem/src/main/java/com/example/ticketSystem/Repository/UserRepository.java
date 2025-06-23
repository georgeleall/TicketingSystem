package com.example.ticketSystem.Repository;

import com.example.ticketSystem.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByName(String name);
}

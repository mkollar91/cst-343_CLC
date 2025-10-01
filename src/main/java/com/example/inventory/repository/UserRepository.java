package com.example.inventory.repository;

import com.example.inventory.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    java.util.Optional<User> findByUsername(String username);
}

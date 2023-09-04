package com.example.demo.auth.repository;

import com.example.demo.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findUserByUsername(String username);
    Boolean existsByUsername(String username);
}

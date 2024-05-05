package com.example.identityservice.repository;

import com.example.identityservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User , String> {
    boolean existsByUserName(String s);
    Optional<User> findByUserName(String username);
}

package com.auth.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
   Optional<User> findByUsername(String username);
}

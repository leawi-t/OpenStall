package com.project.open_stall.repo;

import com.project.open_stall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);
}

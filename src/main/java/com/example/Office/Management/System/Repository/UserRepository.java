package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFullName(String fullName);
    User findByRole(User.Role role); // Fetch all users with a given role
}

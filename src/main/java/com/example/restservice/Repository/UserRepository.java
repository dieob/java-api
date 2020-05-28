package com.example.restservice.Repository;

import com.example.restservice.Models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User>  findAll(); 
    Optional<User> findByEmail(String email);
}

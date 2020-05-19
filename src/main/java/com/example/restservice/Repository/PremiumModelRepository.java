package com.example.restservice.Repository;

import com.example.restservice.Models.PremiumModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface PremiumModelRepository extends JpaRepository<PremiumModel, Long> {
    List<PremiumModel> findAll();
    Optional<PremiumModel> findById(Long Id);
}

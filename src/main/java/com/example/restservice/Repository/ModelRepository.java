package com.example.restservice.Repository;

import com.example.restservice.Models.Model;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findAll();
    Optional<Model> findById(Long Id);
    List<Model> findTop10ByOrderByRankDesc();
}

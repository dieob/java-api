package com.example.restservice.Repository;

import com.example.restservice.Models.Model;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Diego Baez
 */
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findAll();
    Optional<Model> findById(Long Id);
    List<Model> findTop10ByOrderByRankDesc();
    
    @Query("SELECT m FROM Model m WHERE m.gender = :gender ORDER BY m.rank DESC")
    public List<Model> findBestModels(@Param("gender") String gender);
}


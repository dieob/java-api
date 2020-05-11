package com.example.restservice.Repository;

import com.example.restservice.Models.Model;
import com.example.restservice.Models.ModelReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface ModelReviewRepository extends JpaRepository<ModelReview, Long> {
    List<ModelReview>  findAll(); 
    List<ModelReview> findByModel(Model model);
}

package com.example.restservice.Repository;

import com.example.restservice.Models.Model;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Diego Baez
 */
public interface ModelRepository extends CrudRepository<Model, Long> {
    List<Model> findAll();
    Optional<Model> findById(Long Id);
}

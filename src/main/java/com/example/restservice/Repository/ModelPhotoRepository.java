package com.example.restservice.Repository;

import com.example.restservice.Models.Model;
import com.example.restservice.Models.ModelPhoto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface ModelPhotoRepository extends JpaRepository<ModelPhoto, Long> {
    List<ModelPhoto>  findAll(); 
    List<ModelPhoto> findByModel(Model model);
}

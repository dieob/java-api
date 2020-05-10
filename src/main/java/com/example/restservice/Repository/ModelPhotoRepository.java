package com.example.restservice.Repository;

import com.example.restservice.Models.ModelPhoto;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Diego Baez
 */
public interface ModelPhotoRepository extends CrudRepository<ModelPhoto, Long> {
    List<ModelPhoto>  findAll(); 
}

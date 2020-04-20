package com.example.restservice.Repository;

import com.example.restservice.Models.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Diego Baez
 */
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAll();
    Optional<Post> findById(Long Id);
}

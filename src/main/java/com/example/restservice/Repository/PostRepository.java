package com.example.restservice.Repository;

import com.example.restservice.Models.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();
    Optional<Post> findById(Long Id);
    List<Post> findTop10ByOrderByRankDesc();
}
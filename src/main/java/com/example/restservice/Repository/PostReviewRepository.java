package com.example.restservice.Repository;

import com.example.restservice.Models.Post;
import com.example.restservice.Models.PostReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface PostReviewRepository extends JpaRepository<PostReview, Long> {
    List<PostReview>  findAll(); 
    List<PostReview> findByPost(Post post);
}

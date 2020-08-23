package com.example.restservice.Repository;

import com.example.restservice.Models.Post;
import com.example.restservice.Models.PostPhoto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface PostPhotoRepository extends JpaRepository<PostPhoto, Long> {
    List<PostPhoto>  findAll(); 
    List<PostPhoto> findByPost(Post post);
    //void saveAll(ModelPhoto[] modelPhotos);
}

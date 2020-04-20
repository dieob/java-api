package com.example.restservice.Controllers;

import com.example.restservice.Models.Post;
import com.example.restservice.Models.PostRequest;
import com.example.restservice.Repository.PostRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {;

    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/posts")
    public List<Post> post() {
        List<Post> retrievedPosts  = postRepository.findAll();
        return retrievedPosts;
    }

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<String> createPost(@RequestBody PostRequest request){
        Post newPost = new Post();
        newPost.setTitle(request.getTitle());
        newPost.setDescription(request.getDescription());
        newPost.setCity(request.getCity());
        try{
            postRepository.save(newPost);
        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        Optional<Post> retrievedPost = postRepository.findById(id);

        if (!retrievedPost.isPresent()){
            return new ResponseEntity<>("Item not found",HttpStatus.NOT_FOUND);
        }
        try{
            postRepository.delete(retrievedPost.get());
        }
        catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
    
    @PutMapping("/post/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @Valid @RequestBody Post postDetails) {
        Optional<Post> retrievedPost = postRepository.findById(id);
        Post updatedPost = new Post();
        
        if (!retrievedPost.isPresent()){
            return new ResponseEntity<>("Item not found",HttpStatus.NOT_FOUND);
        }
        try{
            updatedPost.setId(id);
            updatedPost.setTitle(postDetails.getTitle());
            updatedPost.setDescription(postDetails.getDescription());
            updatedPost.setCity(postDetails.getCity());
            postRepository.save(updatedPost);
        }
        catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}
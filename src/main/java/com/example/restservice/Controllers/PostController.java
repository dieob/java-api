package com.example.restservice.Controllers;

import com.example.restservice.Models.Post;
import com.example.restservice.Models.PostRequest;
import com.example.restservice.Repository.PostRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		return postRepository.findAll();
	}
        
        @PostMapping("/post")
        @ResponseBody
        @Transactional
        public void createPost(@RequestBody PostRequest request){
            Post newPost = new Post();
            newPost.setTitle(request.getTitle());
            newPost.setDescription(request.getDescription());
            newPost.setCity(request.getCity());
            entityManager.persist(newPost);
        }
}
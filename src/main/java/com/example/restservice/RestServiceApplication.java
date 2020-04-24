package com.example.restservice;

import com.example.restservice.Models.Post;
import com.example.restservice.Repository.PostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}
        
        @Bean
        ApplicationRunner applicationRunner(PostRepository postRepository){
            return args -> {
                postRepository.save(new Post("Title1", "Desc1", "City1"));
                postRepository.save(new Post("Title2", "Post2", "City2"));
            };
        }
}

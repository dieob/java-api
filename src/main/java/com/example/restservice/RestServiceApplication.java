package com.example.restservice;

import com.example.restservice.Models.Post;
import com.example.restservice.Repository.PostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}
        
        @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/posts").allowedOrigins("*");
			}
		};
	}
        /*
        @Bean
        ApplicationRunner applicationRunner(PostRepository postRepository){
            return args -> {
                postRepository.save(new Post("Title1", "Desc1", "City1"));
                postRepository.save(new Post("Title2", "Desc2", "City2"));
            };
        }*/
}

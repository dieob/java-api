package com.example.restservice.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author Diego Baez
 */
@Data
@Entity
public class Post {
    
    public Post() {
    }
    

    public Post(String description, String title, String city) {
        this.description = description;
        this.title = title;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String city;
}

package com.example.restservice.Models;

import javax.persistence.Column;
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
    

    public Post(String title, String description, String city) {
        this.description = description;
        this.title = title;
        this.city = city;
    }

    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    private String title;
    
    @Column
    private String description;
    
    @Column
    private String city;
}

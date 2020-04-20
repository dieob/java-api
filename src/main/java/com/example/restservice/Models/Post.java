package com.example.restservice.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Diego Baez
 */
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }   
}

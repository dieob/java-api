package com.example.restservice.Models;

/**
 *
 * @author Diego Baez
 */
public class PostRequest {

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

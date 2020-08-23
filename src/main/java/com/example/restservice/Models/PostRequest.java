package com.example.restservice.Models;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Diego Baez
 */
@Data
public class PostRequest {

    private Long id;
    private String name;
    private String instagram;
    private String twitter;
    private Double stars;
    private Double rank;    
    private List<PostReview> reviewList;
    private List<String> photoList;
    private String city;
}

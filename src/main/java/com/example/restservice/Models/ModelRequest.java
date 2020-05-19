package com.example.restservice.Models;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Diego Baez
 */
@Data
public class ModelRequest {

    private Long id;
    private String name;
    private String instagram;
    private String twitter;
    private int stars;
    private List<ModelReview> reviewList;
    private List<String> photoList;
    private String gender;
}

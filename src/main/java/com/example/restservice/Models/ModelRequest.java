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
    private int stars;
    private List<String> reviewList;
    private List<String> photoList;
}

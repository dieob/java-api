package com.example.restservice.Models;

import java.util.Date;
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
public class Model {
    
    public Model() {
    }
    

    public Model(String name, String instagram, int stars) {
        this.name = name;
        this.instagram = instagram;
        this.stars = stars;
    }

    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    private String name;
    
    @Column
    private String instagram;
    
    @Column
    private int stars;
    
    @Column
    private Date createdDate;
}

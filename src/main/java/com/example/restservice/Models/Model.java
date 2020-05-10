package com.example.restservice.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Diego Baez
 */
@Data
@Entity
@Table(name = "models")
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
    
    @OneToMany(mappedBy = "model")
    @JsonIgnore
    private Set<ModelPhoto> photos;
}

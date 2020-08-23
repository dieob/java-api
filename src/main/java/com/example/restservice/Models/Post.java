package com.example.restservice.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "posts")
public class Post {
    
    public Post() {
    }

    public Post(String name, String instagram, String twitter, String city, Double stars) {
        this.name = name;
        this.instagram = instagram;
        this.stars = stars;
        this.twitter = twitter;
        this.city = city;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String instagram;
    
    @Column
    private String twitter;

    @Column
    private Double stars;
    
    @Column
    private Double rank;
    
    @Column
    private String city;
    

    @Column
    @JsonIgnore
    private Date createdDate;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<PostPhoto> photos;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<PostReview> reviews;
}

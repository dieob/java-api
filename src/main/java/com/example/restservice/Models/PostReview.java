/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author diego
 */
@Data
@Entity
@Table (name="post_reviews")
public class PostReview {

    public PostReview() {
    }

    public PostReview(String review, Post post, int stars) {
        this.review = review;
        this.post = post;
        this.stars = stars;
    }
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

   @Lob
    private String review;
    
    @Column
    private int stars;
    
    @Column
    private Date createdDate;

}

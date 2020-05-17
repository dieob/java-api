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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author diego
 */
@Data
@Entity
@Table(name = "reviews")
public class ModelReview {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @Column
    private String review;
    
    @Column
    private int stars;
    
    @Column
    private Date createdDate;

    public ModelReview() {
    }

    public ModelReview(String review, Model model, int stars) {
        this.review = review;
        this.model = model;
        this.stars = stars;
    }

}

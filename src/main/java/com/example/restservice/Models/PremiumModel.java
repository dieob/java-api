/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.Models;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author diego
 */
@Data
@Entity
@Table(name="premium_models")
public class PremiumModel implements Serializable {
    
    public PremiumModel() {
    }
    
    
        
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;
    
    @Column
    private String gender;

    @Column
    private String instagram;
    
    @Column
    private String twitter;
    
    @Column
    private ArrayList<String> links;
    
    @Column 
    private String title; 
    
    @Column(columnDefinition = "LONGTEXT")
    private String message;
    
    @Column(columnDefinition = "LONGTEXT")
    private String image;
       
}

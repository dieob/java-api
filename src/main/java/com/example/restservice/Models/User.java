/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.Models;

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
@Table(name = "users_")
public class User {

    public User() {
    }
    
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.isVerified = false;
    }
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String email;

    @Column
    private String password;
    
    @Column 
    private Boolean isVerified;
    
}

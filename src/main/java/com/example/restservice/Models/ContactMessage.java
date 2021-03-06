/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.Models;

import java.io.Serializable;
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
@Table(name = "contact_messages")
public class ContactMessage implements Serializable {

    public ContactMessage() {
    }

    public ContactMessage(String senderEmail, String message) {
        this.senderEmail = senderEmail;
        this.message = message;
        this.responseSent = false;
    }
    
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String senderEmail;

    @Column
    private String message;
    
    @Column
    private boolean responseSent;
}

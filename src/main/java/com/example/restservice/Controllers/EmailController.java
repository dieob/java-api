/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.Controllers;

import com.example.restservice.Models.ContactMessage;
import com.example.restservice.Models.PremiumModel;
import com.example.restservice.Repository.ContactMessageRepository;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author diego
 */
@RestController
public class EmailController {
   
   @Autowired
   ContactMessageRepository contactMessageRepository;
           
   @PostMapping("/contact")
   public ResponseEntity<String> contactForm(String email, String message) {
       ContactMessage contactMessage = new ContactMessage(email, message);
       
       contactMessageRepository.save(contactMessage);
      
      return new ResponseEntity<>("Contact form received", HttpStatus.OK);
   }  
   
    @GetMapping("/messages")
    public ResponseEntity<List<ContactMessage>> messages() {
        List<ContactMessage> retrievedMessages = contactMessageRepository.findAll();
        
        return new ResponseEntity<>(retrievedMessages, HttpStatus.OK);
    }
    
    
   @PostMapping("/sendemail")
   public ResponseEntity<String> sendEmail(String email, String message) {
       try {
           sendmail(email, message);
       } catch (IOException | MessagingException ex) {
           Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
           return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
       }
      return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
   }  
   
   
   private void sendmail(String email, String message) throws AddressException, MessagingException, IOException {
   
    String fullMessage;
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    
    fullMessage = message + " sent by: "+ email;

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
       protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("dbaezrenault@gmail.com", "052291052291");
       }
    });

    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(email, true));

    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dbaezrenault@gmail.com"));
    msg.setSubject("New Email from user");
    msg.setSentDate(new Date());

    MimeBodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setContent(fullMessage, "text/html");

    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);
    //MimeBodyPart attachPart = new MimeBodyPart();

    //attachPart.attachFile("/var/tmp/image19.png");
    //multipart.addBodyPart(attachPart);
    msg.setContent(multipart);
    Transport.send(msg);   
   }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.Controllers;

import com.example.restservice.Models.User;
import com.example.restservice.Repository.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
public class UserController {
    
   public static final String SALT = "hash.salt.value";
   
   @Autowired
   private Environment env;
   
   @Autowired
   UserRepository userRepository;
           
   
   @GetMapping("/users")
   public ResponseEntity<List<User>> users() {
        List<User> retrievedUsers = userRepository.findAll();
        
        return new ResponseEntity<>(retrievedUsers, HttpStatus.OK);
   }
   
   @PostMapping("/authenticate")
   public ResponseEntity<String> authenticate(String email, String password){
       String loginStatus;
       
       try {
           loginStatus = login(email, password);
       
            if(loginStatus.equals("Success")){
                return new ResponseEntity<>("Login Success", HttpStatus.OK);
            } else if (loginStatus.equals("Failed")){
                return new ResponseEntity<>("Login Failed", HttpStatus.UNAUTHORIZED);
            }

            //if the user does not exist it will be signed up automatically
            signup(email, password);
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
            
       } catch (NoSuchAlgorithmException ex) {
           Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
           return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   

   public void signup(String email, String password) throws NoSuchAlgorithmException {
       
    String salt = env.getProperty(SALT);
       
    String saltedPassword = salt + password;
    String hashedPassword = generateHash(saltedPassword);
    
    User createdUser = new User(email, hashedPassword);
    userRepository.save(createdUser);
      
      return;
   }  
   

   public String login(String email, String password) throws NoSuchAlgorithmException {
        // remember to use the same SALT value use used while storing password
        // for the first time.
        
        String salt = env.getProperty(SALT);
        String saltedPassword = salt + password;
        String hashedPassword;
        
        hashedPassword = generateHash(saltedPassword);
       
       Optional<User> retrievedUser = userRepository.findByEmail(email);
       
       if(!retrievedUser.isPresent()){
          return "User does not exist"; 
       }

        String storedPasswordHash = retrievedUser.get().getPassword();
        if(hashedPassword.equals(storedPasswordHash)){
            return "Success";
        }else{
            return "Failed";
        }
   }
   
   public static String generateHash(String input) throws NoSuchAlgorithmException {
        StringBuilder hash = new StringBuilder();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = sha.digest(input.getBytes());
        char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
        for (int idx = 0; idx < hashedBytes.length; ++idx) {
            byte b = hashedBytes[idx];
            hash.append(digits[(b & 0xf0) >> 4]);
            hash.append(digits[b & 0x0f]);
        }

        return hash.toString();
    }
  
}

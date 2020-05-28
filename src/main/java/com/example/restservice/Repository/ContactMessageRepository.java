package com.example.restservice.Repository;

import com.example.restservice.Models.ContactMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Diego Baez
 */
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage>  findAll(); 
    List<ContactMessage> findByResponseSent(boolean isSent);
}

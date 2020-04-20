/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.Repository;

import com.example.restservice.Models.Post;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Diego Baez
 */
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAll();
}

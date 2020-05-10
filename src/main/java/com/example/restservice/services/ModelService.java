/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.services;

import com.example.restservice.Models.Model;
import com.example.restservice.Models.ModelPhoto;
import com.example.restservice.Repository.ModelPhotoRepository;
import com.example.restservice.Repository.ModelRepository;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

/**
 *
 * @author diego
 */
@Service
@Transactional
public class ModelService {

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ModelPhotoRepository modelPhotoRepository;
    
    @Autowired
    EntityManager em;

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveNewModel(Model model, ModelPhoto modelPhoto) {
        //Set<ModelPhoto> photos = new HashSet<ModelPhoto>();
        //photos.add(modelPhoto);
        //model.setPhotos(photos);
        
        em.persist(model);
        //modelPhoto.setModel(model);
        //em.persist(modelPhoto);
        modelPhoto.setModel(model);
        em.persist(modelPhoto);

       // modelRepository.save(model);
       // modelPhoto.setModel(model);
       // modelPhotoRepository.save(modelPhoto);
    }
}

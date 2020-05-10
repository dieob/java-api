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
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveNewModel(Model model, ModelPhoto modelPhoto) {
        modelRepository.save(model);
        modelPhoto.setModel(model);
        modelPhotoRepository.save(modelPhoto);
    }
}

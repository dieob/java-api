/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.services;

import com.example.restservice.Models.Model;
import com.example.restservice.Models.ModelPhoto;
import com.example.restservice.Models.ModelRequest;
import com.example.restservice.Models.ModelReview;
import com.example.restservice.Repository.ModelPhotoRepository;
import com.example.restservice.Repository.ModelRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.restservice.Repository.ModelReviewRepository;
import java.util.ArrayList;
import java.util.List;

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
    ModelReviewRepository modelReviewRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public ModelRequest saveNewModel(Model model, List<ModelPhoto> modelPhotos, ModelReview modelReview, List<String> encodedPhotos, String review) {
        
        modelRepository.save(model);
        for(ModelPhoto photo : modelPhotos){
            photo.setModel(model);
            modelPhotoRepository.save(photo);  
        }
        modelReview.setModel(model);
        modelReviewRepository.save(modelReview);
        
        ModelRequest result = new ModelRequest();
        List<String> reviewsToList = new ArrayList<>();
        reviewsToList.add(review);
        result.setId(model.getId());
        result.setName(model.getName());
        result.setInstagram(model.getInstagram());
        result.setStars(model.getStars());
        result.setPhotoList(encodedPhotos);
        result.setReviewList(reviewsToList);
        
        return result;
    }
}

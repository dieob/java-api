/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.services;

import static com.example.restservice.Controllers.UserController.SALT;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.env.Environment;

/**
 *
 * @author diego
 */
@Service
@Transactional
public class ModelService {
    
    public static final String MAX_FILE_SIZE = "max.size.file.upload";

    @Autowired
    private Environment env;
   
    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ModelPhotoRepository modelPhotoRepository;
    
    @Autowired
    ModelReviewRepository modelReviewRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public ModelRequest saveNewModel(Model model, List<ModelPhoto> modelPhotos, ModelReview modelReview, List<String> encodedPhotos) {
        
        modelRepository.save(model);
        for(ModelPhoto photo : modelPhotos){
            photo.setModel(model);
            modelPhotoRepository.save(photo);  
        }
        modelReview.setModel(model);
        modelReviewRepository.save(modelReview);
        
        ModelRequest result = new ModelRequest();
        List<ModelReview> reviewsToList = new ArrayList<>();
        reviewsToList.add(modelReview);
        result.setId(model.getId());
        result.setName(model.getName());
        result.setInstagram(model.getInstagram());
        result.setTwitter(model.getTwitter());
        result.setStars(model.getStars());
        result.setPhotoList(encodedPhotos);
        result.setReviewList(reviewsToList);
        result.setGender(model.getGender());
        
        return result;
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public ModelReview saveNewReview(ModelReview review, Model modelToUpdate){
        modelReviewRepository.save(review);
        
        //recalculate rating of model with this newly saved review and update
        modelToUpdate.setStars(calculateStarsMean(modelToUpdate));
        modelToUpdate.setRank(ratingFormula(modelToUpdate));
        modelRepository.save(modelToUpdate);
        
        return review;
    }
    
    private Double calculateStarsMean(Model model){
        List<ModelReview> modelsReviews = modelReviewRepository.findByModel(model);
        
        Double sum = 0.0;
        Double result = 0.0;
        for (ModelReview review : modelsReviews){
            sum = sum + review.getStars();
        }
        
        result = (sum)/(modelsReviews.size());
        return result;
    }
    
    public Double ratingFormula(Model model){
        
        //Formula: (R * v + C * m) / (v + m)
        //R= stars mean (of model)
        //C= R mean (of all)
        //v=number of reviews of model
        //m=minimum reviews to be considered
    
        List<Model> allModels = modelRepository.findAll();
        List<ModelReview> modelsReviews = modelReviewRepository.findByModel(model);
        ArrayList<Double> modelsRmean = new ArrayList<>();
        //Calculate R
        Double iterativeR; //to get R for all models for next step
        for (Model model1 : allModels){
            iterativeR = calculateStarsMean(model1);
            modelsRmean.add(iterativeR);
        }
        
        Double R = calculateStarsMean(model); //current model stars mean
        
        //Calculate C
        Double sum = 0.0;
        Double C = 0.0;
        for (Double r : modelsRmean){
            sum = sum+r;
        }
        
        C = sum/modelsRmean.size();
        
        //calculate v
        int v = modelsReviews.size();
        
        //set value for m
        int m = 10;
        
        Double rating = ((R*v)+(C*m))/(v+m);
        
        return rating;
        
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public void updateAllRanks(){
        List<Model> allModels = modelRepository.findAll();
        
        for(Model model: allModels){
            model.setRank(ratingFormula(model));
        }
        
        modelRepository.saveAll(allModels);
    }
    
}

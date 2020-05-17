package com.example.restservice.Controllers;

import com.example.restservice.Models.Model;
import com.example.restservice.Models.ModelPhoto;
import com.example.restservice.Models.ModelRequest;
import com.example.restservice.Models.ModelReview;
import com.example.restservice.Repository.ModelPhotoRepository;
import com.example.restservice.Repository.ModelRepository;
import com.example.restservice.services.ModelService;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.restservice.Repository.ModelReviewRepository;
import java.util.ArrayList;

@RestController
public class ModelController {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelPhotoRepository modelPhotoRepository;

    @Autowired
    private ModelReviewRepository modelReviewRepository;

    @Autowired
    private ModelService service;

    @GetMapping("/models")
    public ResponseEntity<List<ModelRequest>> models() {
        List<Model> retrievedModels = modelRepository.findAll();

        List<ModelRequest> result = new ArrayList<>();

        for (int i = 0; i < retrievedModels.size(); i++) {
            ModelRequest modelRequest = new ModelRequest();
            List<ModelPhoto> modelPhotos = new ArrayList<>();
            List<ModelReview> modelReviews = new ArrayList<>();
            List<String> photoList = new ArrayList<>();
            List<ModelReview> reviewList = new ArrayList<>();

            modelRequest.setId(retrievedModels.get(i).getId());
            modelRequest.setName(retrievedModels.get(i).getName());
            modelRequest.setInstagram(retrievedModels.get(i).getInstagram());
            modelRequest.setStars(retrievedModels.get(i).getStars());

            modelPhotos = modelPhotoRepository.findByModel(retrievedModels.get(i));
            modelReviews = modelReviewRepository.findByModel(retrievedModels.get(i));

            for (ModelPhoto photo : modelPhotos) {
                photoList.add(photo.getImage());
            }

            for (ModelReview review : modelReviews) {
                reviewList.add(review);
            }

            modelRequest.setPhotoList(photoList);
            modelRequest.setReviewList(reviewList);

            result.add(modelRequest);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
        @GetMapping("/model/{id}")
        public ResponseEntity<ModelRequest> getSingleModel(@PathVariable Long id) {
        Optional<Model> retrievedModel = modelRepository.findById(id);

        if (!retrievedModel.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); 
        }

            ModelRequest modelRequest = new ModelRequest();
            List<ModelPhoto> modelPhotos = new ArrayList<>();
            List<ModelReview> modelReviews = new ArrayList<>();
            List<String> photoList = new ArrayList<>();
            List<ModelReview> reviewList = new ArrayList<>();

            modelRequest.setId(retrievedModel.get().getId());
            modelRequest.setName(retrievedModel.get().getName());
            modelRequest.setInstagram(retrievedModel.get().getInstagram());
            modelRequest.setStars(retrievedModel.get().getStars());

            modelPhotos = modelPhotoRepository.findByModel(retrievedModel.get());
            modelReviews = modelReviewRepository.findByModel(retrievedModel.get());

            for (ModelPhoto photo : modelPhotos) {
                photoList.add(photo.getImage());
            }

            for (ModelReview review : modelReviews) {
                reviewList.add(review);
            }

            modelRequest.setPhotoList(photoList);
            modelRequest.setReviewList(reviewList);

            
        return new ResponseEntity<>(modelRequest, HttpStatus.OK);
    }
    
    @GetMapping("/bestmodels")
    public ResponseEntity<List<ModelRequest>> bestModels() {
        List<Model> retrievedModels = modelRepository.findTop3ByOrderByStarsDesc();

        List<ModelRequest> result = new ArrayList<>();

        for (int i = 0; i < retrievedModels.size(); i++) {
            ModelRequest modelRequest = new ModelRequest();
            List<ModelPhoto> modelPhotos = new ArrayList<>();
            List<ModelReview> modelReviews = new ArrayList<>();
            List<String> photoList = new ArrayList<>();
            List<ModelReview> reviewList = new ArrayList<>();

            modelRequest.setId(retrievedModels.get(i).getId());
            modelRequest.setName(retrievedModels.get(i).getName());
            modelRequest.setInstagram(retrievedModels.get(i).getInstagram());
            modelRequest.setStars(retrievedModels.get(i).getStars());

            modelPhotos = modelPhotoRepository.findByModel(retrievedModels.get(i));
            modelReviews = modelReviewRepository.findByModel(retrievedModels.get(i));

            for (ModelPhoto photo : modelPhotos) {
                photoList.add(photo.getImage());
            }

            for (ModelReview review : modelReviews) {
                reviewList.add(review);
            }

            modelRequest.setPhotoList(photoList);
            modelRequest.setReviewList(reviewList);

            result.add(modelRequest);
        }
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/photos")
    public List<ModelPhoto> modelsPhotos() {
        List<ModelPhoto> retrieved = modelPhotoRepository.findAll();
        return retrieved;
    }

    @GetMapping("/reviews")
    public List<ModelReview> modelsReviews() {
        List<ModelReview> retrieved = modelReviewRepository.findAll();
        return retrieved;
    }
    
    @PostMapping("/review")
    @Transactional
    public ResponseEntity<ModelReview> createReview(String review, Long modelId, int stars){
        
        Optional<Model> retrievedModel = modelRepository.findById(modelId);
        ModelReview newReview = new ModelReview();
        if(retrievedModel.isPresent()){
            newReview.setReview(review);
            newReview.setStars(stars);
            retrievedModel.get().setStars(service.calculateStars(retrievedModel.get(), stars));
            newReview.setModel(retrievedModel.get());
            newReview.setCreatedDate(new Date());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ModelReview savedReview = service.saveNewReview(newReview, retrievedModel.get());
        return new ResponseEntity<>(savedReview, HttpStatus.OK);
    }

    @PostMapping("/model")
    @Transactional
    public ResponseEntity<ModelRequest> createPost(@RequestParam("file1") Optional<MultipartFile> file1,@RequestParam("file2") Optional<MultipartFile> file2,@RequestParam("file3") Optional<MultipartFile> file3, String name, String instagram, int stars, String review) {

        Model newModel = new Model();

        List<ModelPhoto> photos = new ArrayList<>();
        newModel.setName(name);
        newModel.setInstagram(instagram);
        newModel.setStars(stars);
        newModel.setCreatedDate(new Date());

        ModelReview newReview = new ModelReview();
        newReview.setReview(review);
        newReview.setCreatedDate(new Date());
        newReview.setStars(stars);

        String encodedString;
        List<String> encodedPhotos = new ArrayList<>();
        try {
            if(file1.isPresent()){
                encodedString = Base64.getEncoder().encodeToString(file1.get().getBytes());
                photos.add(new ModelPhoto(encodedString, null));
                encodedPhotos.add(encodedString);
            }
            if(file2.isPresent()){
                encodedString = Base64.getEncoder().encodeToString(file2.get().getBytes());
                photos.add(new ModelPhoto(encodedString, null));
                encodedPhotos.add(encodedString);
            }
            if(file3.isPresent()){
                encodedString = Base64.getEncoder().encodeToString(file3.get().getBytes());
                photos.add(new ModelPhoto(encodedString, null));
                encodedPhotos.add(encodedString);
            }
            
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        ModelRequest createdModel = service.saveNewModel(newModel, photos, newReview, encodedPhotos);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }

    @DeleteMapping("/model/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        Optional<Model> retrieved = modelRepository.findById(id);

        if (!retrieved.isPresent()) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        try {
            modelRepository.delete(retrieved.get());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/model/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @Valid @RequestBody Model modelDetails) {
        Optional<Model> retrieved = modelRepository.findById(id);
        Model updated = new Model();

        if (!retrieved.isPresent()) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        try {
            updated.setId(id);
            updated.setName(modelDetails.getName());
            updated.setInstagram(modelDetails.getInstagram());
            updated.setStars(modelDetails.getStars());
            modelRepository.save(updated);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}

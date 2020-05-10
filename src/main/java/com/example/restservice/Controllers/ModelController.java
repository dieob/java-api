package com.example.restservice.Controllers;

import com.example.restservice.Models.Model;
import com.example.restservice.Models.ModelPhoto;
import com.example.restservice.Repository.ModelPhotoRepository;
import com.example.restservice.Repository.ModelRepository;
import com.example.restservice.services.ModelService;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

@RestController
public class ModelController {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelPhotoRepository modelPhotoRepository;

    @PersistenceContext
    EntityManager entityManager;
    
    @Autowired
    private ModelService service;

    @GetMapping("/models")
    public List<Model> models() {
        List<Model> retrieved = modelRepository.findAll();
        return retrieved;
    }
    
    @GetMapping("/photos")
    public List<ModelPhoto> modelsPhotos() {
        List<ModelPhoto> retrieved = modelPhotoRepository.findAll();
        return retrieved;
    }

    @PostMapping("/model")
    @Transactional
    public ResponseEntity<String> createPost(@RequestParam("file") MultipartFile file, String name, String instagram, int stars) {
        
        Model newModel = new Model();
        
        ModelPhoto photo = new ModelPhoto();
        newModel.setName(name);
        newModel.setInstagram(instagram);
        newModel.setStars(stars);
        newModel.setCreatedDate(new Date());

        String encodedString;
        try {
            encodedString = Base64.getEncoder().encodeToString(file.getBytes());
            byte[] imageByte=Base64.getDecoder().decode(encodedString);
            photo.setImage(encodedString);
            //photo.setModel(newModel);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //newModel.setPhotos(photos);
        service.saveNewModel(newModel, photo);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
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

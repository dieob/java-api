package com.example.restservice.Controllers;

import com.example.restservice.Models.Model;
import com.example.restservice.Models.ModelRequest;
import com.example.restservice.Repository.ModelRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ModelController {

    ;

    @Autowired
    private ModelRepository modelRepository;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/models")
    public List<Model> models() {
        List<Model> retrieved = modelRepository.findAll();
        return retrieved;
    }

    @PostMapping("/model")
    public ResponseEntity<String> createPost(@RequestParam("file") MultipartFile file, String name, String instagram, int stars) {
        Model newModel = new Model();
        newModel.setName(name);
        newModel.setInstagram(instagram);
        newModel.setStars(stars);
        newModel.setCreatedDate(new Date());
        try {
            modelRepository.save(newModel);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

package com.example.restservice.Controllers;

import com.example.restservice.Models.Post;
import com.example.restservice.Models.PostPhoto;
import com.example.restservice.Models.PostReview;
import com.example.restservice.Models.PostRequest;
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
import com.example.restservice.Repository.PostPhotoRepository;
import com.example.restservice.Repository.PostRepository;
import com.example.restservice.Repository.PostReviewRepository;
import com.example.restservice.services.PostService;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostPhotoRepository postPhotoRepository;

    @Autowired
    private PostReviewRepository postReviewRepository;

    @Autowired
    private PostService service;

    @GetMapping("/posts")
    @Transactional
    public ResponseEntity<List<PostRequest>> posts() {
        List<Post> retrievedPosts = postRepository.findAll();

        List<PostRequest> result = new ArrayList<>();

        for (int i = 0; i < retrievedPosts.size(); i++) {
            PostRequest postRequest = new PostRequest();
            List<PostPhoto> postPhotos = new ArrayList<>();
            List<PostReview> postReviews = new ArrayList<>();
            List<String> photoList = new ArrayList<>();
            List<PostReview> reviewList = new ArrayList<>();

            postRequest.setId(retrievedPosts.get(i).getId());
            postRequest.setName(retrievedPosts.get(i).getName());
            postRequest.setInstagram(retrievedPosts.get(i).getInstagram());
            postRequest.setTwitter(retrievedPosts.get(i).getTwitter());
            postRequest.setStars(retrievedPosts.get(i).getStars());
            postRequest.setCity(retrievedPosts.get(i).getCity());
            postRequest.setRank(retrievedPosts.get(i).getRank());

            postPhotos = postPhotoRepository.findByPost(retrievedPosts.get(i));
            postReviews = postReviewRepository.findByPost(retrievedPosts.get(i));

            for (PostPhoto photo : postPhotos) {
                photoList.add(photo.getImage());
            }

            for (PostReview review : postReviews) {
                reviewList.add(review);
            }

            postRequest.setPhotoList(photoList);
            postRequest.setReviewList(reviewList);

            result.add(postRequest);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
        @GetMapping("/post/{id}")
        @Transactional
        public ResponseEntity<PostRequest> getSinglePost(@PathVariable Long id) {
        Optional<Post> retrievedPost = postRepository.findById(id);

        if (!retrievedPost.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); 
        }

            PostRequest postRequest = new PostRequest();
            List<PostPhoto> postPhotos = new ArrayList<>();
            List<PostReview> postReviews = new ArrayList<>();
            List<String> photoList = new ArrayList<>();
            List<PostReview> reviewList = new ArrayList<>();

            postRequest.setId(retrievedPost.get().getId());
            postRequest.setName(retrievedPost.get().getName());
            postRequest.setInstagram(retrievedPost.get().getInstagram());
            postRequest.setTwitter(retrievedPost.get().getTwitter());
            postRequest.setStars(retrievedPost.get().getStars());
            postRequest.setCity(retrievedPost.get().getCity());

            postPhotos = postPhotoRepository.findByPost(retrievedPost.get());
            postReviews = postReviewRepository.findByPost(retrievedPost.get());

            for (PostPhoto photo : postPhotos) {
                photoList.add(photo.getImage());
            }

            for (PostReview review : postReviews) {
                reviewList.add(review);
            }

            postRequest.setPhotoList(photoList);
            postRequest.setReviewList(reviewList);

            
        return new ResponseEntity<>(postRequest, HttpStatus.OK);
    }
    
    @GetMapping("/bestposts")
    @Transactional
    public ResponseEntity<List<PostRequest>> bestPosts() {
        
        List<Post> retrievedPosts;
        //call service that updates rank for all posts
        service.updateAllRanks();
        
        retrievedPosts = postRepository.findTop10ByOrderByRankDesc();
        List<PostRequest> result = new ArrayList<>();

        for (int i = 0; i < retrievedPosts.size(); i++) {
            PostRequest postRequest = new PostRequest();
            List<PostPhoto> postPhotos = new ArrayList<>();
            List<PostReview> postReviews = new ArrayList<>();
            List<String> photoList = new ArrayList<>();
            List<PostReview> reviewList = new ArrayList<>();

            postRequest.setId(retrievedPosts.get(i).getId());
            postRequest.setName(retrievedPosts.get(i).getName());
            postRequest.setInstagram(retrievedPosts.get(i).getInstagram());
            postRequest.setTwitter(retrievedPosts.get(i).getTwitter());
            postRequest.setStars(retrievedPosts.get(i).getStars());
            postRequest.setCity(retrievedPosts.get(i).getCity());
            postRequest.setRank(retrievedPosts.get(i).getRank());

            postPhotos = postPhotoRepository.findByPost(retrievedPosts.get(i));
            postReviews = postReviewRepository.findByPost(retrievedPosts.get(i));

            for (PostPhoto photo : postPhotos) {
                photoList.add(photo.getImage());
            }

            for (PostReview review : postReviews) {
                reviewList.add(review);
            }

            postRequest.setPhotoList(photoList);
            postRequest.setReviewList(reviewList);

            result.add(postRequest);
        }
        
        if(result.size() > 10){
            result = result.subList(0, 10);
        }
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/allphotos")
    @Transactional
    public List<PostPhoto> postPhotos() {
        List<PostPhoto> retrieved = postPhotoRepository.findAll();
        return retrieved;
    }

    @GetMapping("/allreviews")
    @Transactional
    public List<PostReview> postReviews() {
        List<PostReview> retrieved = postReviewRepository.findAll();
        return retrieved;
    }
    
    @PostMapping("/postreview")
    @Transactional
    public ResponseEntity<PostReview> createPostReview(String review, Long postId, int stars){
        
        Optional<Post> retrievedPost = postRepository.findById(postId);
        PostReview newReview = new PostReview();
        if(retrievedPost.isPresent()){
            newReview.setReview(review);
            newReview.setStars(stars);
            newReview.setPost(retrievedPost.get());
            newReview.setCreatedDate(new Date());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PostReview savedReview = service.saveNewReview(newReview, retrievedPost.get());
        return new ResponseEntity<>(savedReview, HttpStatus.OK);
    }
    
    private String checkSocial(String username){
        if (username.substring(0, 1).equals("@")){
            return username.substring(1, username.length());
        } else{
            return (username);
        }
    }
    
    private String checkName(String name){
        String[] words = name.split(" ");
        String currentWord = "";
        String result = "";
        String wordCopy ="";
        
        for (String word : words){
            if(word.length() > 1){
                wordCopy  = word.substring(1, word.length());
            } else if(word.length()==1){
                wordCopy = "";
            }

            if(word.length()==0){
                currentWord = "";
            } else {
                if(!Character.isUpperCase(word.codePointAt(0))){
                    currentWord = word.subSequence(0, 1).toString().toUpperCase() + wordCopy;
                } else {
                    currentWord = word;
                }
            }
            result = result + " " + currentWord;
        }
        
        if(!Character.isUpperCase(result.codePointAt(0))){
            result = result.substring(1, result.length());
        }
        
        return result;
    }

    @PostMapping("/post")
    @Transactional
    public ResponseEntity<PostRequest> createPost(@RequestParam("file1") Optional<MultipartFile> file1,@RequestParam("file2") Optional<MultipartFile> file2,@RequestParam("file3") Optional<MultipartFile> file3, String name, String instagram, String twitter, int stars, String review, String city) {

        Post newPost = new Post();

        List<PostPhoto> photos = new ArrayList<>();
        newPost.setName(checkName(name));
        if(instagram.length() > 0){
            newPost.setInstagram(checkSocial(instagram));
        } else {
            newPost.setInstagram("Not available");
        }
        
        if(twitter.length()>0){
            newPost.setTwitter(checkSocial(twitter));
        } else {
            newPost.setTwitter("Not available");
        }
        newPost.setStars(Double.valueOf(stars));
        newPost.setRank(0.0); //initialize new items with no rank
        newPost.setCreatedDate(new Date());
        newPost.setCity(city);

        PostReview newReview = new PostReview();
        newReview.setReview(review);
        newReview.setCreatedDate(new Date());
        newReview.setStars(stars);

        String encodedString;
        List<String> encodedPhotos = new ArrayList<>();
        try {
            if(file1.isPresent()){
                encodedString = Base64.getEncoder().encodeToString(file1.get().getBytes());
                photos.add(new PostPhoto(encodedString, null));
                encodedPhotos.add(encodedString);
            }
            if(file2.isPresent()){
                encodedString = Base64.getEncoder().encodeToString(file2.get().getBytes());
                photos.add(new PostPhoto(encodedString, null));
                encodedPhotos.add(encodedString);
            }
            if(file3.isPresent()){
                encodedString = Base64.getEncoder().encodeToString(file3.get().getBytes());
                photos.add(new PostPhoto(encodedString, null));
                encodedPhotos.add(encodedString);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        PostRequest createdPost = service.saveNewPost(newPost, photos, newReview, encodedPhotos);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/post/{id}")
    @Transactional
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        Optional<Post> retrieved = postRepository.findById(id);
        List<PostPhoto> photos = new ArrayList<>();
        List<PostReview> reviews = new ArrayList<>();
        if (!retrieved.isPresent()) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        try {
            //delete the photos first
            photos = postPhotoRepository.findByPost(retrieved.get());
            postPhotoRepository.deleteAll(photos);
            
            //delete the reviews first
            reviews = postReviewRepository.findByPost(retrieved.get());
            postReviewRepository.deleteAll(reviews);
            
            //then delete the post
            postRepository.delete(retrieved.get());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/post/{id}")
    @Transactional
    public ResponseEntity<String> updatePost(@PathVariable Long id, @Valid @RequestBody Post postDetails) {
        Optional<Post> retrieved = postRepository.findById(id);
        Post updated = new Post();

        if (!retrieved.isPresent()) {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        try {
            updated.setId(id);
            updated.setName(postDetails.getName());
            updated.setInstagram(postDetails.getInstagram());
            updated.setStars(postDetails.getStars());
            postRepository.save(updated);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}

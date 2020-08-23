package com.example.restservice.services;

import com.example.restservice.Models.Post;
import com.example.restservice.Models.PostPhoto;
import com.example.restservice.Models.PostRequest;
import com.example.restservice.Models.PostReview;
import com.example.restservice.Repository.PostPhotoRepository;
import com.example.restservice.Repository.PostRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.restservice.Repository.PostReviewRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.env.Environment;

/**
 *
 * @author diego
 */
@Service
@Transactional
public class PostService {
    
    public static final String MAX_FILE_SIZE = "max.size.file.upload";

    @Autowired
    private Environment env;
   
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostPhotoRepository postPhotoRepository;
    
    @Autowired
    PostReviewRepository postReviewRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public PostRequest saveNewPost(Post post, List<PostPhoto> postPhotos, PostReview postReview, List<String> encodedPhotos) {
        
        postRepository.save(post);
        for(PostPhoto photo : postPhotos){
            photo.setPost(post);
            postPhotoRepository.save(photo);  
        }
        postReview.setPost(post);
        postReviewRepository.save(postReview);
        
        PostRequest result = new PostRequest();
        List<PostReview> reviewsToList = new ArrayList<>();
        reviewsToList.add(postReview);
        result.setId(post.getId());
        result.setName(post.getName());
        result.setInstagram(post.getInstagram());
        result.setTwitter(post.getTwitter());
        result.setStars(post.getStars());
        result.setPhotoList(encodedPhotos);
        result.setReviewList(reviewsToList);
        result.setCity(post.getCity());
        
        return result;
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public PostReview saveNewReview(PostReview review, Post postToUpdate){
        postReviewRepository.save(review);
        
        //recalculate rating of model with this newly saved review and update
        postToUpdate.setStars(calculateStarsMean(postToUpdate));
        postToUpdate.setRank(ratingFormula(postToUpdate));
        postRepository.save(postToUpdate);
        
        return review;
    }
    
    private Double calculateStarsMean(Post post){
        List<PostReview> postsReviews = postReviewRepository.findByPost(post);
        
        Double sum = 0.0;
        Double result = 0.0;
        for (PostReview review : postsReviews){
            sum = sum + review.getStars();
        }
        
        result = (sum)/(postsReviews.size());
        return result;
    }
    
    public Double ratingFormula(Post post){
        
        //Formula: (R * v + C * m) / (v + m)
        //R= stars mean (of model)
        //C= R mean (of all)
        //v=number of reviews of model
        //m=minimum reviews to be considered
    
        List<Post> allPosts = postRepository.findAll();
        List<PostReview> postReviews = postReviewRepository.findByPost(post);
        ArrayList<Double> postsRmean = new ArrayList<>();
        //Calculate R
        Double iterativeR; //to get R for all models for next step
        for (Post post1 : allPosts){
            iterativeR = calculateStarsMean(post1);
            postsRmean.add(iterativeR);
        }
        
        Double R = calculateStarsMean(post); //current model stars mean
        
        //Calculate C
        Double sum = 0.0;
        Double C = 0.0;
        for (Double r : postsRmean){
            sum = sum+r;
        }
        
        C = sum/postsRmean.size();
        
        //calculate v
        int v = postReviews.size();
        
        //set value for m
        int m = 10;
        
        Double rating = ((R*v)+(C*m))/(v+m);
        
        return rating;
        
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public void updateAllRanks(){
        List<Post> allPosts = postRepository.findAll();
        
        for(Post post: allPosts){
            post.setRank(ratingFormula(post));
        }
        
        postRepository.saveAll(allPosts);
    }
    
}

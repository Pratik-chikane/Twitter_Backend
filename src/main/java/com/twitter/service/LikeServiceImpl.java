package com.twitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.model.Like;
import com.twitter.model.Tweet;
import com.twitter.model.User;
import com.twitter.repository.LikeRepository;
import com.twitter.repository.TweetRepository;


@Service
public class LikeServiceImpl implements ILikeService {

	@Autowired
	private LikeRepository likeRepo;
	
	@Autowired
	private TweetRepository tweetRepo;
	
	@Autowired
	private ITweetService tweetService;
	
	
	@Override
	public Like likeAndUnlikeTweet(Long tweetId, User user) {
		Like isLikeExists = likeRepo.isLikeAlreadyExists(user.getId(), tweetId); 
		
		if (isLikeExists != null) {
			likeRepo.deleteById(isLikeExists.getId());
			return isLikeExists;
		}
		Tweet tweet = tweetService.findById(tweetId);
		Like like = new Like();
		like.setTweet(tweet);
		like.setUser(user);
		
		Like savedLike = likeRepo.save(like);
		
		tweet.getLikes().add(savedLike);
		tweetRepo.save(tweet);
		
		
		return savedLike;
	}

	@Override
	public List<Like> getAllLikesByTweet(Long tweetId) {
		Tweet tweet = tweetService.findById(tweetId);
		
		List<Like> likes = likeRepo.findByLikeByTweet(tweetId);
		return likes;
	}

}

package com.twitter.service;

import java.util.List;

import com.twitter.model.Like;
import com.twitter.model.User;

public interface ILikeService {
	
	public Like likeAndUnlikeTweet(Long tweetId,User user);
	
	public List<Like> getAllLikesByTweet(Long tweetId);

}

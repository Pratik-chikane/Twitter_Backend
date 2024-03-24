package com.twitter.service;

import java.util.List;

import com.twitter.dto.TweetReplayRequest;
import com.twitter.model.Tweet;
import com.twitter.model.User;

public interface ITweetService {
	
	public Tweet createTweet(Tweet req,User user);
	
	public List<Tweet> findAllTweets();
	
	public Tweet createRetweet(Long tweetId, User user);
	
	public Tweet findById(Long tweetId);
	
	public void deleteTweetById(Long tweetId, Long userId);

	public Tweet createdReplay(TweetReplayRequest req, User user);
	
	public List<Tweet> getUserTweet(User user);
	
	public List<Tweet> findByLikesContainingUser(User user);
	
	public List<Tweet> findByBookmarksContainingUser(User user);

}

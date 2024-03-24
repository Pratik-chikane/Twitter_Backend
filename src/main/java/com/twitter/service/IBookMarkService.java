package com.twitter.service;

import java.util.List;

import com.twitter.model.Bookmark;
import com.twitter.model.User;

public interface IBookMarkService {
	
	public Bookmark createOrRemoveBookmarkByTweet(Long tweetId,User user );
	
	public List<Bookmark> getBookmarkByUser(Long userId);

}

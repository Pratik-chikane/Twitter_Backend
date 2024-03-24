package com.twitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.model.Bookmark;
import com.twitter.model.Tweet;
import com.twitter.model.User;
import com.twitter.repository.BookmarkRepository;
import com.twitter.repository.TweetRepository;


@Service
public class BookmarkServiceImpl implements IBookMarkService {
	
	@Autowired
	private BookmarkRepository bookmarkRepo;
	
	@Autowired
	private ITweetService tweetService;
	
	@Autowired
	private TweetRepository tweetRepo;

	@Override
	public Bookmark createOrRemoveBookmarkByTweet(Long tweetId, User user) {
		Tweet tweet = tweetService.findById(tweetId);
		Bookmark isAlreadyExists = bookmarkRepo.isBookmarkAlreadyExists(user.getId(), tweetId);
		
		if(isAlreadyExists != null) {
			bookmarkRepo.deleteById(isAlreadyExists.getId());
			return isAlreadyExists;
		}
		Bookmark bookmark = new Bookmark();
		bookmark.setTweet(tweet);
		bookmark.setUser(user);
		
		Bookmark savedBookMark = bookmarkRepo.save(bookmark);
		tweet.getBookmark().add(savedBookMark);
		tweetRepo.save(tweet);
		return savedBookMark;
		
		
	}

	@Override
	public List<Bookmark> getBookmarkByUser(Long userId) {
		
		
		return bookmarkRepo.findByBookmarkByUser(userId);
	}

}

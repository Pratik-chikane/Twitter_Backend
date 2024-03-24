package com.twitter.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.dto.TweetReplayRequest;
import com.twitter.exceptioin.TweetException;
import com.twitter.exceptioin.UserException;
import com.twitter.model.Tweet;
import com.twitter.model.User;
import com.twitter.repository.TweetRepository;

@Service
public class TweetServiceImpl implements ITweetService {

	@Autowired
	private TweetRepository tweetRepo;

	@Override
	public Tweet createTweet(Tweet req, User user) {

		Tweet tweet = new Tweet();
		tweet.setContent(req.getContent());
		tweet.setCreatedAt(LocalDateTime.now());
		tweet.setImage(req.getImage());
		tweet.setUser(user);
		tweet.setReplay(false);
		tweet.setTweet(true);
		tweet.setVideo(req.getVideo());

		return tweetRepo.save(tweet);
	}

	@Override
	public List<Tweet> findAllTweets() {

		return tweetRepo.findAllByIsTweetTrueOrderByCreatedAtDesc();
	}

	@Override
	public Tweet createRetweet(Long tweetId, User user) {
		Tweet tweet = findById(tweetId);
		if (tweet.getRetweetUser().contains(user)) {
			tweet.getRetweetUser().remove(user);
		} else {
			tweet.getRetweetUser().add(user);
		}

		return tweetRepo.save(tweet);
	}

	@Override
	public Tweet findById(Long tweetId) {
		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new TweetException("Tweet not found with id " + tweetId));
		return tweet;
	}

	@Override
	public void deleteTweetById(Long tweetId, Long userId) {
		Tweet tweet = findById(tweetId);
		if (!userId.equals(tweet.getUser().getId())) {
			throw new UserException("You can't delete another user's tweet");
		}
		tweetRepo.deleteById(tweetId);

	}

	@Override
	public Tweet createdReplay(TweetReplayRequest req, User user) {
		Tweet replayFor = findById(req.getTweetId());
		Tweet tweet = new Tweet();
		tweet.setContent(req.getContent());
		tweet.setCreatedAt(LocalDateTime.now());
		tweet.setImage(req.getImage());
		tweet.setUser(user);
		tweet.setReplay(true);
		tweet.setTweet(false);
		tweet.setVideo(req.getVideo());
		tweet.setReplayFor(replayFor);

		Tweet savedReplay = tweetRepo.save(tweet);

		replayFor.getReplayTweets().add(savedReplay);
	
	

		tweetRepo.save(replayFor);
		return replayFor;
	}

	@Override
	public List<Tweet> getUserTweet(User user) {
		return tweetRepo.findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(user, user.getId());
	}

	@Override
	public List<Tweet> findByLikesContainingUser(User user) {
		
		return tweetRepo.findByLikesUser_Id(user.getId());
	}
	@Override
	public List<Tweet> findByBookmarksContainingUser(User user) {
		
		return tweetRepo.findByBookmarksUser_Id(user.getId());
	}

}

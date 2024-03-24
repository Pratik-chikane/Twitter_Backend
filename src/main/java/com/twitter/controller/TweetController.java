package com.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.dto.ApiResponse;
import com.twitter.dto.TweetDTO;
import com.twitter.dto.TweetReplayRequest;
import com.twitter.mapper.TweetDtoMapper;
import com.twitter.model.Tweet;
import com.twitter.model.User;
import com.twitter.service.ITweetService;
import com.twitter.service.IUserService;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

	@Autowired
	private ITweetService tweetService;

	@Autowired
	private IUserService userService;

	@PostMapping("/create")
	public ResponseEntity<TweetDTO> createTweet(@RequestBody Tweet req, @RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.createTweet(req, user);

		TweetDTO tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		return new ResponseEntity<TweetDTO>(tweetDto, HttpStatus.CREATED);
	}

	@PostMapping("/replay")
	public ResponseEntity<TweetDTO> replayTweet(@RequestBody TweetReplayRequest req,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.createdReplay(req, user);

		TweetDTO tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		return new ResponseEntity<TweetDTO>(tweetDto, HttpStatus.CREATED);
	}

	@PutMapping("/{tweetId}/retweet")
	public ResponseEntity<TweetDTO> createRetweet(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.createRetweet(tweetId, user);

		TweetDTO tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		return new ResponseEntity<TweetDTO>(tweetDto, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{tweetId}")
	public ResponseEntity<TweetDTO> findTweetById(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.findById(tweetId);

		TweetDTO tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		return new ResponseEntity<TweetDTO>(tweetDto, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<TweetDTO>> getAllTweets(@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		List<Tweet> tweets = tweetService.findAllTweets();
		

		List<TweetDTO> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		for(TweetDTO tweet: tweetDtos) {
			if(tweet.getUser().getId() == user.getId()) {
				tweet.getUser().setReq_user(true);
			}
		}
		return new ResponseEntity<List<TweetDTO>>(tweetDtos, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TweetDTO>> getUsersAllTweets(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserById(userId);
		List<Tweet> tweets = tweetService.getUserTweet(user);

		List<TweetDTO> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		return new ResponseEntity<List<TweetDTO>>(tweetDtos, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<TweetDTO>> getAllLikedTweetsByUser(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserById(userId);
		List<Tweet> tweets = tweetService.findByLikesContainingUser(user);

		List<TweetDTO> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		return new ResponseEntity<List<TweetDTO>>(tweetDtos, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/bookmarks")
	public ResponseEntity<List<TweetDTO>> getAllBookmarkedTweetsByUser(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		List<Tweet> tweets = tweetService.findByBookmarksContainingUser(user);
		System.out.println("***********************8");
		for(Tweet tweet : tweets) {
			System.out.println("TWEET BOOKMARK "+tweet.getUser().getId());
		}

		List<TweetDTO> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		return new ResponseEntity<List<TweetDTO>>(tweetDtos, HttpStatus.OK);
	}

	@DeleteMapping("/{tweetId}")
	public ResponseEntity<ApiResponse> deleteTweetById(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		tweetService.deleteTweetById(tweetId, user.getId());
		ApiResponse res = new ApiResponse("Tweet deleted successfully", true);

		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}

}

package com.twitter.mapper;

import java.util.ArrayList;
import java.util.List;

import com.twitter.dto.TweetDTO;
import com.twitter.dto.UserDTO;
import com.twitter.model.Tweet;
import com.twitter.model.User;
import com.twitter.util.RelativeTimeConvertor;
import com.twitter.util.TweetUtil;

public class TweetDtoMapper {
	
	public static TweetDTO toTweetDto(Tweet tweet,User reqUser) {
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		
		UserDTO userDto = UserDtoMapper.toUserDto(tweet.getUser());
		System.out.println(userDto+"ASSSSSSSSSSFFIEWAFWWWWWWWWWWWWWWWWWWWw");
		
		boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);
		
		boolean isBookmarked = TweetUtil.isBookmarkedByReqUser(reqUser, tweet);
		
		boolean isRetweeted = TweetUtil.isRetweetByReqUser(reqUser, tweet);
		
		List<Long> retweetUserId = new ArrayList<>();
		
		for(User user: tweet.getRetweetUser()) {
			retweetUserId.add(user.getId());
		}
		TweetDTO tweetDto = new TweetDTO();
		tweetDto.setId(tweet.getId());
		tweetDto.setContent(tweet.getContent());
		tweetDto.setCreatedAt(tweet.getCreatedAt());
		tweetDto.setImage(tweet.getImage());
		tweetDto.setTotalLikes(tweet.getLikes().size());
		tweetDto.setTotalReplies(tweet.getReplayTweets().size());

		tweetDto.setTweetCreatedAt(RelativeTimeConvertor.relativeTime(tweet.getCreatedAt().toString()));

		tweetDto.setTotalRetweets(tweet.getRetweetUser().size());
		tweetDto.setUser(userDto);
		tweetDto.setLiked(isLiked);
		tweetDto.setBookmarked(isBookmarked);
		tweetDto.setRetweet(isRetweeted);
		tweetDto.setRetweetUserId(retweetUserId);
		tweetDto.setReplayTweets(toTweetDtos(tweet.getReplayTweets(),reqUser));
		tweetDto.setVideo(tweet.getVideo());		
		
		return tweetDto;
	}

	public static List<TweetDTO> toTweetDtos(List<Tweet> tweets, User reqUser) {
		List<TweetDTO> tweetDtos = new ArrayList<>();
		for(Tweet tweet: tweets) {
			TweetDTO tweetDto = toReplayTweetDto(tweet,reqUser);
			tweetDtos.add(tweetDto);
		}
		return tweetDtos;
	}

	private static TweetDTO toReplayTweetDto(Tweet tweet, User reqUser) {
UserDTO userDto = UserDtoMapper.toUserDto(tweet.getUser());
		
		boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);
		
		boolean isBookmarked = TweetUtil.isBookmarkedByReqUser(reqUser, tweet);
		
		boolean isRetweeted = TweetUtil.isRetweetByReqUser(reqUser, tweet);
		
		List<Long> retweetUserId = new ArrayList<>();
		
		for(User user: tweet.getRetweetUser()) {
			retweetUserId.add(user.getId());
		}
		TweetDTO tweetDto = new TweetDTO();
		tweetDto.setId(tweet.getId());
		tweetDto.setContent(tweet.getContent());
		tweetDto.setCreatedAt(tweet.getCreatedAt());
		tweetDto.setImage(tweet.getImage());
		tweetDto.setTotalLikes(tweet.getLikes().size());
		tweetDto.setTotalReplies(tweet.getReplayTweets().size());
		tweetDto.setTweetCreatedAt(RelativeTimeConvertor.relativeTime(tweet.getCreatedAt().toString()));
		tweetDto.setTotalRetweets(tweet.getRetweetUser().size());
		tweetDto.setUser(userDto);
		tweetDto.setLiked(isLiked);
		tweetDto.setBookmarked(isBookmarked);
		tweetDto.setRetweet(isRetweeted);
		tweetDto.setRetweetUserId(retweetUserId);
		tweetDto.setVideo(tweet.getVideo());		
		
		return tweetDto;
	}

}

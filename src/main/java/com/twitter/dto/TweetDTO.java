package com.twitter.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class TweetDTO {
	
	private Long id;
	
	private String content;
	private String image;
	private String video;
	private UserDTO user;
	private LocalDateTime createdAt;
	private int totalLikes;
	private int totalReplies;
	private String tweetCreatedAt;
	
	private int totalRetweets;
	private boolean isLiked;
	private boolean isBookmarked;
	private boolean isRetweet;
	private List<Long> retweetUserId;
	private List<TweetDTO>replayTweets;

}

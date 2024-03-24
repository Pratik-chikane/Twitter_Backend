package com.twitter.dto;

import lombok.Data;

@Data
public class BookmarkDTO {
	
	private Long id;
	private UserDTO user;
	private TweetDTO tweet;

}

package com.twitter.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@Hidden
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;
	
	private String content;
	private String image;
	private LocalDateTime createdAt;

	private String video;
	
	private boolean isTweet;
	private boolean isReplay;       
	@OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();

	@OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
	private List<Bookmark> bookmark = new ArrayList<>();

	@OneToMany
	private List<Tweet> replayTweets = new ArrayList<>();

	@ManyToMany
	private List<User> retweetUser = new ArrayList<>();

	@ManyToOne
	private Tweet replayFor;

	@Override
	public String toString() {
	    return "Tweet [id=" + id +", content=" + content + ", image=" + image + ", createdAt="
	            + createdAt + ", video=" + video + ", isTweet=" + isTweet + ", isReplay=" + isReplay +
	            ", likes=" + likes.stream().map(Like::getId).collect(Collectors.toList()) +
	            ", bookmark=" + bookmark +
	            ", replayTweets=" + replayTweets.stream().map(Tweet::getId).collect(Collectors.toList()) +
	            ", retweetUser=" + retweetUser.stream().map(User::getId).collect(Collectors.toList()) +
	            ", replayFor=" + (replayFor != null ? replayFor.getId() : null) +
	            "]";
	}

}

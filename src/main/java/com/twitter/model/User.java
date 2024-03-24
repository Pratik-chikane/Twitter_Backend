package com.twitter.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Hidden
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	private String location;
	private String website;
	private String bio;
	private String dateOfBirth;
	private String email;
	private String password;
	private String mobile;
	private String profileImage;
	private String backgroundImage;
	private String req_user;
	private LocalDateTime createdAt;
	private boolean login_with_google;
	private boolean login_with_facebook;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Like> like = new ArrayList<>();
	
	@Embedded
	private Verification verification;
	
	@JsonIgnore
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Bookmark> bookmark = new ArrayList<>();
	
	@ManyToMany
	@JsonIgnore
	private List<User> followers = new ArrayList<>();
	
	@ManyToMany
	@JsonIgnore
	private List<User> following = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Tweet> tweet = new ArrayList<>();

	
	@Override
	public String toString() {
	    return "User{" +
	            "id=" + id +
	            ", fullName='" + fullName + '\'' +
	            ", location='" + location + '\'' +
	            ", website='" + website + '\'' +
	            ", bio='" + bio + '\'' +
	            ", dateOfBirth='" + dateOfBirth + '\'' +
	            ", email='" + email + '\'' +
	            ", password='" + password + '\'' +
	            ", mobile='" + mobile + '\'' +
	            ", profileImage='" + profileImage + '\'' +
	            ", backgroundImage='" + backgroundImage + '\'' +
	            ", req_user='" + req_user + '\'' +
	            ", createdAt=" + createdAt +
	            ", login_with_google=" + login_with_google +
	            ", login_with_facebook=" + login_with_facebook +
	            ", verification=" + verification +
	            ", like=" + like.stream().map(Like::getId).collect(Collectors.toList()) +
	            ", bookmark=" + bookmark.stream().map(Bookmark::getId).collect(Collectors.toList()) +
	            ", followers=" + followers.stream().map(User::getId).collect(Collectors.toList()) +
	            ", following=" + following.stream().map(User::getId).collect(Collectors.toList()) +
	            ", tweet=" + tweet.stream().map(Tweet::getId).collect(Collectors.toList()) +
	            '}';
	}

	
}

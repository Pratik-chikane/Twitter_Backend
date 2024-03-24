package com.twitter.dto;

import java.util.ArrayList;
import java.util.List;

import com.twitter.model.User;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	private String fullName;
	private String email;
	private String location;
	private String joinedSince;
	private String profileImage;
	private String bio;
	private String dateOfBirth;
	private String mobile;
	private String backgroundImage;
	private String website;
	private boolean req_user;
	private boolean login_with_google;
	private boolean login_with_facebook;
	private List<UserDTO> followers =new ArrayList<>();
	private List<UserDTO> following = new ArrayList<>();
	private boolean followed;
	private boolean isVerified;
	private String verifiedTill;
}

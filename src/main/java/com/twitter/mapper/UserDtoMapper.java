package com.twitter.mapper;

import java.util.ArrayList;

import java.util.List;

import com.twitter.dto.UserDTO;
import com.twitter.model.User;
import com.twitter.util.RelativeTimeConvertor;
import com.twitter.util.UserUtil;

public class UserDtoMapper {

	public static UserDTO toUserDto(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());

		userDto.setFullName(user.getFullName());

		userDto.setEmail(user.getEmail());
		userDto.setProfileImage(user.getProfileImage());
		userDto.setBackgroundImage(user.getBackgroundImage());
		userDto.setLocation(user.getLocation());
		userDto.setVerified(UserUtil.isVerified(user.getVerification().getEndsAt()));
		userDto.setBio(user.getBio());
		userDto.setLogin_with_facebook(user.isLogin_with_facebook());
		userDto.setLogin_with_google(user.isLogin_with_google());
		userDto.setDateOfBirth(user.getDateOfBirth());
		userDto.setJoinedSince(RelativeTimeConvertor.formatToMonthYear(user.getCreatedAt().toString()));
		userDto.setFollowers(toUserDtos(user.getFollowers()));
		userDto.setFollowing(toUserDtos(user.getFollowing()));
		if (UserUtil.isVerified(user.getVerification().getEndsAt()) == true)
			userDto.setVerifiedTill(
					RelativeTimeConvertor.formatToDateMonthYear(user.getVerification().getEndsAt().toString()));

		return userDto;

	}

	public static List<UserDTO> toUserDtos(List<User> followers) {
		List<UserDTO> userDtos = new ArrayList<>();

		for (User user : followers) {
			UserDTO userDto = new UserDTO();
			userDto.setFullName(user.getFullName());
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			userDto.setProfileImage(user.getProfileImage());
//			userDto.setJoinedSince(RelativeTimeConvertor.formatToMonthYear(user.getCreatedAt().toString()));
			userDtos.add(userDto);
		}

		return userDtos;
	}

}

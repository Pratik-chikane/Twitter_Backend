package com.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.dto.UserDTO;
import com.twitter.mapper.UserDtoMapper;
import com.twitter.model.User;
import com.twitter.service.IUserService;
import com.twitter.util.UserUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		UserDTO userDto = UserDtoMapper.toUserDto(user);
		userDto.setReq_user(true);
		System.out.println("USERDTP "+userDto);
		return new ResponseEntity<UserDTO>(userDto, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) {
		User reqUser = userService.findUserProfileByJwt(jwt);
		User user = userService.findUserById(userId);
		UserDTO userDto = UserDtoMapper.toUserDto(user);
		userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
		userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
		return new ResponseEntity<UserDTO>(userDto, HttpStatus.ACCEPTED);
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String query,
			@RequestHeader("Authorization") String jwt) {
		User reqUser = userService.findUserProfileByJwt(jwt);
		List<User> users = userService.searchUser(query);

		List<UserDTO> userDtos = UserDtoMapper.toUserDtos(users);

		return new ResponseEntity<List<UserDTO>>(userDtos, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update")
	public ResponseEntity<UserDTO> updateUser(@RequestBody User req, String query,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);

		User updatedUser = userService.UpdateUser(user, req);

		UserDTO userDto = UserDtoMapper.toUserDto(updatedUser);

		return new ResponseEntity<UserDTO>(userDto, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{userId}/follow")
	public ResponseEntity<UserDTO> followUnfollowUser(@PathVariable Long userId, String query,
			@RequestHeader("Authorization") String jwt) {
		User reqUser = userService.findUserProfileByJwt(jwt);
		User user = userService.followAndUnfollowUser(userId, reqUser);

		UserDTO userDto = UserDtoMapper.toUserDto(user);
		userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
		return new ResponseEntity<UserDTO>(userDto, HttpStatus.ACCEPTED);
	}

}

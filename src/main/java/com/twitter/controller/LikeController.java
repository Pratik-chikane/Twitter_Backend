package com.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.dto.LikeDTO;
import com.twitter.mapper.LikeDtoMapper;
import com.twitter.model.Like;
import com.twitter.model.User;
import com.twitter.service.ILikeService;
import com.twitter.service.IUserService;

@RestController
@RequestMapping("/api")
public class LikeController {

	@Autowired
	private ILikeService likeService;

	@Autowired
	private IUserService userService;

	@PostMapping("/{tweetId}/like")
	public ResponseEntity<LikeDTO> likeUnlikeTweet(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		Like like = likeService.likeAndUnlikeTweet(tweetId, user);
		LikeDTO likeDto = LikeDtoMapper.toLikeDto(like, user);

		return new ResponseEntity<LikeDTO>(likeDto, HttpStatus.CREATED);
	}

	@GetMapping("/{tweetId}/likes")
	public ResponseEntity<List<LikeDTO>> getAllTweetLikes(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);
		List<Like> likes = likeService.getAllLikesByTweet(tweetId);
		List<LikeDTO> likeDtos = LikeDtoMapper.toLikeDtos(likes, user);
		return new ResponseEntity<List<LikeDTO>>(likeDtos, HttpStatus.CREATED);
	}

}

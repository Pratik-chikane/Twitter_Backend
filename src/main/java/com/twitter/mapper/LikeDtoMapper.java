package com.twitter.mapper;

import java.util.ArrayList;
import java.util.List;

import com.twitter.dto.LikeDTO;
import com.twitter.dto.TweetDTO;
import com.twitter.dto.UserDTO;
import com.twitter.model.Like;
import com.twitter.model.User;

public class LikeDtoMapper {

	public static LikeDTO toLikeDto(Like like, User reqUser) {
		UserDTO userDto = UserDtoMapper.toUserDto(like.getUser());

		UserDTO reqUserDto = UserDtoMapper.toUserDto(reqUser);
		TweetDTO tweetDto = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser);

		LikeDTO likeDto = new LikeDTO();
		likeDto.setId(like.getId());
		likeDto.setTweet(tweetDto);
		likeDto.setUser(userDto);

		return likeDto;

	}

	public static List<LikeDTO> toLikeDtos(List<Like> likes, User reqUser) {
		List<LikeDTO> likeDtos = new ArrayList<>();

		for (Like like : likes) {
			UserDTO userDto = UserDtoMapper.toUserDto(like.getUser());
			TweetDTO tweetDto = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser);
			LikeDTO likeDto = new LikeDTO();
			likeDto.setId(like.getId());
			likeDto.setTweet(tweetDto);
			likeDto.setUser(userDto);
			likeDtos.add(likeDto);

		}

		return likeDtos;

	}

}

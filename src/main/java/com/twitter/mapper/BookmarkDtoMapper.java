package com.twitter.mapper;

import java.util.ArrayList;
import java.util.List;

import com.twitter.dto.BookmarkDTO;
import com.twitter.dto.TweetDTO;
import com.twitter.dto.UserDTO;
import com.twitter.model.Bookmark;
import com.twitter.model.User;

public class BookmarkDtoMapper {

	public static BookmarkDTO toBookmarkDto(Bookmark bookmark, User reqUser) {
		UserDTO userDto = UserDtoMapper.toUserDto(bookmark.getUser());

		UserDTO reqUserDto = UserDtoMapper.toUserDto(reqUser);
		TweetDTO tweetDto = TweetDtoMapper.toTweetDto(bookmark.getTweet(), reqUser);

		BookmarkDTO bookmarkDto = new BookmarkDTO();
		bookmarkDto.setId(bookmark.getId());
		bookmarkDto.setTweet(tweetDto);
		bookmarkDto.setUser(userDto);

		return bookmarkDto;

	}

	public static List<BookmarkDTO> toBookmarkDtos(List<Bookmark> bookmarks, User reqUser) {
		List<BookmarkDTO> bookmarkDtos = new ArrayList<>();

		for (Bookmark bookmark : bookmarks) {
			UserDTO userDto = UserDtoMapper.toUserDto(bookmark.getUser());
			TweetDTO tweetDto = TweetDtoMapper.toTweetDto(bookmark.getTweet(), reqUser);
			BookmarkDTO bookmarkDto = new BookmarkDTO();
			bookmarkDto.setId(bookmark.getId());
			bookmarkDto.setTweet(tweetDto);
			bookmarkDto.setUser(userDto);

			bookmarkDtos.add(bookmarkDto);

		}

		return bookmarkDtos;

	}

}

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

import com.twitter.dto.BookmarkDTO;
import com.twitter.mapper.BookmarkDtoMapper;
import com.twitter.model.Bookmark;
import com.twitter.model.User;
import com.twitter.service.IBookMarkService;
import com.twitter.service.IUserService;



@RestController
@RequestMapping("/api")
public class BookmarkController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IBookMarkService bookmarkService;

	@PostMapping("/{tweetId}/bookmark")
	public ResponseEntity<BookmarkDTO> bookmarkAndUnbookmarkTweet(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);

		Bookmark bookmark = bookmarkService.createOrRemoveBookmarkByTweet(tweetId, user);

		BookmarkDTO bookmarkDto = BookmarkDtoMapper.toBookmarkDto(bookmark, user);

		return new ResponseEntity<BookmarkDTO>(bookmarkDto, HttpStatus.CREATED);
	}

	@GetMapping("/{tweetId}/bookmark")
	public ResponseEntity<List<BookmarkDTO>> getAllTweetBookmarks(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserProfileByJwt(jwt);

		List<Bookmark> bookmarks = bookmarkService.getBookmarkByUser(tweetId);

		List<BookmarkDTO> likeDtos = BookmarkDtoMapper.toBookmarkDtos(bookmarks, user);

		return new ResponseEntity<List<BookmarkDTO>>(likeDtos, HttpStatus.OK);
	}

}

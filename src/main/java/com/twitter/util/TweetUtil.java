package com.twitter.util;

import com.twitter.model.Bookmark;
import com.twitter.model.Like;
import com.twitter.model.Tweet;
import com.twitter.model.User;

public class TweetUtil {

	public final static boolean isLikedByReqUser(User user, Tweet tweet) {
		for (Like like : tweet.getLikes()) {
			if (like.getUser().getId().equals(user.getId())) {
				return true;
			}
		}
		return false;
	}

	public final static boolean isBookmarkedByReqUser(User user, Tweet tweet) {
		for (Bookmark bookmark : tweet.getBookmark()) {
			if (bookmark.getUser().getId().equals(user.getId())) {
				return true;
			}
		}
		return false;
	}

	public final static boolean isRetweetByReqUser(User user, Tweet tweet) {
		for (User retweetUser : tweet.getRetweetUser()) {
			if (retweetUser.getId().equals(user.getId())) {
				return true;
			}
		}
		return false;
	}

}

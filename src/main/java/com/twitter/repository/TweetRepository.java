package com.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twitter.model.Tweet;
import com.twitter.model.User;


@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
	
	List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();
	
	List<Tweet> findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user,Long userId);
	
	List<Tweet> findByLikesContainingOrderByCreatedAtDesc(User user);
	
	List<Tweet> findByBookmarkContainingOrderByCreatedAtDesc(User user);
	
	@Query("select t from Tweet t JOIN t.likes l where l.user.id=:userId")
	List<Tweet> findByLikesUser_Id(Long userId);
	
	@Query("select t from Tweet t JOIN t.bookmark b where b.user.id=:userId")
	List<Tweet> findByBookmarksUser_Id(Long userId);
}

package com.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.twitter.model.Bookmark;


@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{
	
	@Query("SELECT b FROM Bookmark b WHERE b.user.id = :userId AND b.tweet.id =:tweetId")
	public Bookmark isBookmarkAlreadyExists(@Param("userId")Long userId,@Param("tweetId")Long tweetId);
	
	@Query("SELECT b FROM Bookmark b WHERE b.tweet.id=:userId")
	public List<Bookmark> findByBookmarkByUser(@Param("userId")Long userId);

}

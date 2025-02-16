package com.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.twitter.model.Like;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
	
	@Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.tweet.id =:tweetId")
	public Like isLikeAlreadyExists(@Param("userId")Long userId,@Param("tweetId")Long tweetId);
	
	@Query("SELECT l FROM Like l WHERE l.tweet.id=:tweetId")
	public List<Like> findByLikeByTweet(@Param("tweetId")Long tweetId);

}

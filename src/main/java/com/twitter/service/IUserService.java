package com.twitter.service;

import java.util.List;

import com.twitter.model.User;

public interface IUserService {
	
	public User findUserById(Long userId);
	
	public User findUserProfileByJwt(String jwt);
	
	public User UpdateUser(User user,User reqUser);
	
	public User followAndUnfollowUser(Long userId,User user);
	
	public List<User> searchUser(String query);

}

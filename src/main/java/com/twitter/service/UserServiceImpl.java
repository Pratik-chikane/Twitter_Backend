package com.twitter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.config.JwtTokenProvider;
import com.twitter.exceptioin.UserException;
import com.twitter.model.User;
import com.twitter.repository.UserRepository;


@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtTokenProvider jwtProvider;

	@Override
	public User findUserById(Long userId) {
		return userRepo.findById(userId).orElseThrow(()->new UserException("User"));
	}

	@Override
	public User findUserProfileByJwt(String jwt) {
		
		String email = jwtProvider.getEmailFromJwt(jwt);
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UserException("User not found with email " + email);
		}
		return user;
	}

	@Override
	public User UpdateUser(User useri, User reqUser) {
		Optional<User> optional = userRepo.findById(useri.getId());
	
		User save =null;

		if(optional.isPresent()) {
			User updatedUser = optional.get();
			if(reqUser.getFullName() != null) {
				updatedUser.setFullName(reqUser.getFullName());
			}
			if(reqUser.getBackgroundImage() != null) {
				updatedUser.setBackgroundImage(reqUser.getBackgroundImage());
			}
			if(reqUser.getProfileImage() != null) {
				updatedUser.setProfileImage(reqUser.getProfileImage());
			}
			if(reqUser.getDateOfBirth() != null) {
				updatedUser.setDateOfBirth(reqUser.getDateOfBirth());
			}
			if(reqUser.getBio() != null) {
				updatedUser.setBio(reqUser.getBio());
			}
			if(reqUser.getLocation() != null) {
				updatedUser.setLocation(reqUser.getLocation());
			}
			if(reqUser.getWebsite() != null) {
				updatedUser.setWebsite(reqUser.getWebsite());
			}
			 save = userRepo.save(updatedUser);
		}
	
		
		return save;
	}

	@Override
	public User followAndUnfollowUser(Long userId, User user) {
		User followToUser = findUserById(userId);
		
		if(user.getFollowing().contains(followToUser) && followToUser.getFollowers().contains(user)) {
			user.getFollowing().remove(followToUser);
			followToUser.getFollowers().remove(user);
		}else {
			user.getFollowing().add(followToUser);
			followToUser.getFollowers().add(user);
		}
		userRepo.save(user);
		userRepo.save(followToUser);
		
		return followToUser;
	}

	@Override
	public List<User> searchUser(String query) {
		return userRepo.searchUser(query);
	}

}

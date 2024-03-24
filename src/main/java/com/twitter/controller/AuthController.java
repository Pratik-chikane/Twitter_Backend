package com.twitter.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.twitter.config.JwtTokenProvider;
import com.twitter.dto.LoginWithGooleRequest;
import com.twitter.exceptioin.UserException;
import com.twitter.model.User;
import com.twitter.model.Verification;
import com.twitter.repository.UserRepository;
import com.twitter.response.AuthResponse;
import com.twitter.service.CustomUserDetailsService;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenProvider jwtProvider;
	
	
	 private static final String GOOGLE_CLIENT_ID = "783972841438-6l1jqf5231g19dhjb099hv5900m22mhe.apps.googleusercontent.com";

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signUp(@RequestBody User user) {
		String email = user.getEmail();
		String password = user.getPassword();
		String dateOfBirth = user.getDateOfBirth();
		String fullName = user.getFullName();
		System.out.println("SIGIN UP " + fullName);

		User isPresent = userRepo.findByEmail(email);
		if (isPresent != null) {
			throw new UserException("Account already exists with this email " + email);
		}
		User newUser = new User();
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setEmail(email);
		newUser.setDateOfBirth(dateOfBirth);
		newUser.setFullName(fullName);

		newUser.setVerification(new Verification());

		newUser.setCreatedAt(LocalDateTime.now());
		User savedUser = userRepo.save(newUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse response = new AuthResponse(token, "token generated");
		return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signIn(@RequestBody User user) {

		String email = user.getEmail();
		String password = user.getPassword();
		System.out.println("SIGNIN++++++++++++++++++++");
		Authentication authentication = authenticate(email, password);
		String token = jwtProvider.generateToken(authentication);

		System.out.println("SIGNIN" + email);

		AuthResponse response = new AuthResponse(token, "token generated");
		System.out.println("RESPONSE" + token);
		return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);

	}

	private Authentication authenticate(String email, String password) {

		UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		if (userDetails == null) {
			throw new BadCredentialsException("Enter valid email");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Enter correct password");
		}

		return new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
	}

	@PostMapping("/signin/google")
	public ResponseEntity<AuthResponse> googleLogin(@RequestBody LoginWithGooleRequest req)
			throws GeneralSecurityException, IOException {

		User user = validateGoogleIdToken(req);

		String email = user.getEmail();
		User existingUser = userRepo.findByEmail(email);

		if (existingUser == null) {

			User newUser = new User();
			newUser.setEmail(email);
			newUser.setProfileImage(user.getProfileImage());
			newUser.setFullName(user.getFullName());
			newUser.setLogin_with_google(true);
			newUser.setPassword(user.getPassword());
			newUser.setCreatedAt(LocalDateTime.now());
			newUser.setVerification(new Verification());
System.out.println("_____________LOGIN NEW USER "+newUser);
			userRepo.save(newUser);
			
		}

//        System.out.println("email ---- "+ existingUser.getEmail()+" jwt - ");

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, user.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token,"login with google");
	

//        System.out.println("email ---- "+ existingUser.getEmail()+" jwt - "+token);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	private User validateGoogleIdToken(LoginWithGooleRequest req) throws GeneralSecurityException, IOException {
		HttpTransport transport = new NetHttpTransport();
		JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
	

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(req.getClientId())).build();

		GoogleIdToken token = verifier.verify(req.getCredential());
		if (req.getCredential() != null) {

			Payload payload = token.getPayload();
			String userId = payload.getSubject();

			System.out.println("User ID: " + userId);

			String email = payload.getEmail();
			boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			String locale = (String) payload.get("locale");
			String familyName = (String) payload.get("family_name");
			String givenName = (String) payload.get("given_name");

			User user = new User();
			user.setProfileImage(pictureUrl);
			user.setEmail(email);
			user.setFullName(name);
			user.setPassword(userId);

			System.out.println("image url - -  " + pictureUrl);

			return user;

		} else {
			throw new CredentialException("invalid id token...");
		}

	}

}

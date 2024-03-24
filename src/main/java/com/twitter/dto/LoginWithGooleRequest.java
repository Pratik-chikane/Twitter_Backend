package com.twitter.dto;

import lombok.Data;

@Data
public class LoginWithGooleRequest {

	private String credential;
	private String clientId;

}
package com.twitter.dto;

import lombok.Data;

@Data
public class EMailMessage {
	
	private String to;
	private String subject;
	private String text;

}

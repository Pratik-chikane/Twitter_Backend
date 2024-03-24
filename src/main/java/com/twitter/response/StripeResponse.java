package com.twitter.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StripeResponse {
	  private String sessionId;
}

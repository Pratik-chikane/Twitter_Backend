package com.twitter.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;


@Data
@Hidden
public class Verification {
	
	private boolean status = false;
	private LocalDateTime startedAt;
	private LocalDateTime endsAt;
	private String planType;
}

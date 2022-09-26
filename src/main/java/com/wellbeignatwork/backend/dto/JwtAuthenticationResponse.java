package com.wellbeignatwork.backend.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {
	private String accessToken;
	private UserInfo user;
}
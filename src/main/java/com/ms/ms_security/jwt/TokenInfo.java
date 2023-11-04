package com.ms.ms_security.jwt;

import lombok.Builder;

@Builder
public record TokenInfo(String grantType, String accessToken, String refreshToken, String message) {
}

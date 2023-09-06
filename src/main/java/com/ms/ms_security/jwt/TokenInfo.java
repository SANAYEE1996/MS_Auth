package com.ms.ms_security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record TokenInfo(String grantType, String accessToken, String refreshToken, String message) {
}

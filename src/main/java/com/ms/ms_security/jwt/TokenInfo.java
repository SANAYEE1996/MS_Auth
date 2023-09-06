package com.ms.ms_security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public record TokenInfo(String grantType, String accessToken, String refreshToken, String message) {
}

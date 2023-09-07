package com.ms.ms_security.util;

import lombok.Builder;

@Builder
public record ResponseDto(Integer code, String message, ResponseBody<?> body) {
}

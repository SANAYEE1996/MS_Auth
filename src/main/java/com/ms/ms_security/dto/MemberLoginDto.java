package com.ms.ms_security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {

    @NotBlank(message = "memberEmail must not Blank")
    private String memberEmail;

    @NotBlank(message = "memberPassword must not Blank")
    private String memberPassword;
}

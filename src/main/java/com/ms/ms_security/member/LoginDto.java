package com.ms.ms_security.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message = "memberEmail must not Blank")
    private String email;

    @NotBlank(message = "memberPassword must not Blank")
    private String password;
}

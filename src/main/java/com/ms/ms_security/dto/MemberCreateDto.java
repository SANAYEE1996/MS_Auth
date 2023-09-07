package com.ms.ms_security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDto {

    @NotBlank(message = "email must not Blank")
    private String email;

    @NotBlank(message = "password must not Blank")
    private String password;

    @NotBlank(message = "name must not Blank")
    private String name;
}

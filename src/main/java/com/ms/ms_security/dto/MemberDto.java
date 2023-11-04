package com.ms.ms_security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @NotNull(message = "id must not NULL")
    private Long id;

    @NotBlank(message = "name must not Blank")
    private String name;

    @NotBlank(message = "password must not Blank")
    private String password;
}

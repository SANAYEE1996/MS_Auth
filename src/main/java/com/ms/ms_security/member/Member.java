package com.ms.ms_security.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    private Long id;

    private String email;

    private String password;

    private String name;

}

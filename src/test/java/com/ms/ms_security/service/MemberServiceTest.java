package com.ms.ms_security.service;

import com.ms.ms_security.jwt.TokenInfo;
import com.ms.ms_security.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("save test")
    void saveTest(){
        String email = "dudtkd0219@gmail.com";
        String password = "1234";
        String name = "박영상";
    }

    @Test
    @DisplayName("update test")
    void updateTest(){
        Long id = 1L;
        String email = "dudtkd0219@gmail.com";
        String name = "박영상분신";
        String password = "5678";

    }

    @Test
    @DisplayName("delete test")
    void deleteTest(){
        String email = "dudtkd0218@gmail.com";
        String name = "킴킴킴";
        String password = "1234";
    }

    @Test
    @DisplayName("login test")
    void loginTest(){
        String email = "dudtkd0219@gmail.com";
        String password = "1234";

        TokenInfo tokenInfo =  memberService.login(email,password).block();

        System.out.println(tokenInfo.accessToken());
        System.out.println(tokenInfo.grantType());
        System.out.println(tokenInfo.message());
        System.out.println(tokenInfo.refreshToken());
    }
}

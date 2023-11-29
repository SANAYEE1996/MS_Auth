package com.ms.ms_security.service;

import com.ms.ms_security.entity.Member;
import com.ms.ms_security.jwt.TokenInfo;
import com.ms.ms_security.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("save test")
    void saveTest(){
        String email = "qwer1234@gmail.com";
        String password = "1234";
        String name = "easy_name";

        String saveResult = memberService.saveMember(email, password, name).block();
        assertThat(saveResult).isEqualTo("member save success");

        Member member = memberRepository.findByEmail(email).block();
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);

    }

    @Test
    @DisplayName("update test")
    void updateTest(){
        Long id = 3L;
        String email = "dudtkd0219@gmail.com";
        String name = "박영상분신";
        String password = "5678";

        String updateResult = memberService.updateMember(id, name, password).block();
        assertThat(updateResult).isEqualTo("member update success");

        Member member = memberRepository.findById(id).block();
        assertThat(member.getName()).isEqualTo(name);

        TokenInfo tokenInfo =  memberService.login(email,password).block();
        assertThat(tokenInfo).isNotNull();

    }

    @Test
    @DisplayName("delete test")
    void deleteTest(){
        String email = "dudtkd0218@gmail.com";
        String name = "킴킴킴";
        String password = "1234";

        memberService.saveMember(email, password, name).block();

        Member member = memberRepository.findByEmail(email).block();

        String deleteResult = memberService.deleteMember(member.getId()).block();
        assertThat(deleteResult).isEqualTo("member delete success");

        Member member1 = memberRepository.findByEmail(email).block();
        assertThat(member1).isNull();
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

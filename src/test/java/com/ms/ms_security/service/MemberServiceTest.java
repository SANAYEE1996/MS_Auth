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
    @DisplayName("update test")
    void updateTest(){
        Long id = 1L;
        String email = "dudtkd0219@gmail.com";
        String name = "박영상분신";
        String password = "5678";
        memberService.updateMember(id,name,password);

        Member member = memberRepository.findById(id).orElseThrow(()-> new RuntimeException("not enrolled id"));
        TokenInfo tokenInfo = memberService.login(email, password);

        assertThat(member.getMemberName()).isEqualTo(name);
        assertThat(tokenInfo.accessToken()).isNotBlank();
    }

    @Test
    @DisplayName("delete test")
    void deleteTest(){
        String email = "dudtkd0218@gmail.com";
        String name = "킴킴킴";
        String password = "1234";
        memberService.saveMember(email, password, name);
        Member member = memberRepository.findByMemberEmail(email).orElseThrow(()-> new RuntimeException("not enrolled id"));
        Long id = member.getId();
        memberService.deleteMember(id);

        assertThat(memberRepository.existsByMemberEmail(email)).isFalse();
    }
}

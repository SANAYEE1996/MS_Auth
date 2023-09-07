package com.ms.ms_security.service;


import com.ms.ms_security.entity.Member;
import com.ms.ms_security.jwt.JwtTokenProvider;
import com.ms.ms_security.jwt.TokenInfo;
import com.ms.ms_security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenInfo login(String email, String password) throws RuntimeException{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    public Long saveMember(String email, String password, String name){
        if(!memberRepository.existsByMemberEmail(email)){
            return memberRepository.save(Member.builder()
                    .memberEmail(email)
                    .memberPassword(passwordEncoder.encode(password))
                    .memberName(name)
                    .build()).getId();
        }
        throw new RuntimeException("중복된 이메일 입니다.");
    }

}

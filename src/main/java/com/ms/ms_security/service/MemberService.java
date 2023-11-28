package com.ms.ms_security.service;


import com.ms.ms_security.config.CustomAuthenticationProvider;
import com.ms.ms_security.entity.Member;
import com.ms.ms_security.jwt.JwtTokenProvider;
import com.ms.ms_security.jwt.TokenInfo;
import com.ms.ms_security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final CustomAuthenticationProvider customAuthenticationProvider;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenInfo login(String email, String password) throws RuntimeException{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = customAuthenticationProvider.authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    public Mono<Member> saveMember(String email, String password, String name){
        return memberEmailExists(email).then(memberRepository.save(new Member(null, email, passwordEncoder.encode(password), name)));
    }

    public void updateMember(Long id, String name, String password){

    }

    public void deleteMember(Long id){

    }

    private Mono<Void> memberEmailExists(String email){
        return memberRepository.findByEmail(email)
                .flatMap(member -> {
                    if(member != null){
                        return Mono.error(new RuntimeException(member.getEmail()+" is exists email !"));
                    }
                    return Mono.empty();
                });
    }

}

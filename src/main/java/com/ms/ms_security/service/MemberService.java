package com.ms.ms_security.service;

import com.ms.ms_security.entity.Member;
import com.ms.ms_security.jwt.JwtTokenProvider;
import com.ms.ms_security.jwt.TokenInfo;
import com.ms.ms_security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    public Mono<TokenInfo> login(String email, String password) throws RuntimeException{
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        return memberRepository.findByEmail(token.getPrincipal().toString())
                .switchIfEmpty( Mono.error( new UsernameNotFoundException("User not found")))
                .flatMap(user -> {
                    final String username = token.getPrincipal().toString();
                    final CharSequence rawPassword = token.getCredentials().toString();
                    if( passwordEncoder.matches(rawPassword, user.getPassword())){
                        log.info("User has been authenticated {}", username);
                        return Mono.just( new UsernamePasswordAuthenticationToken(username, user.getPassword(), null) );
                    }
                    return Mono.just( new UsernamePasswordAuthenticationToken(username, token.getCredentials()) );
                }).map(jwtTokenProvider::generateToken);
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

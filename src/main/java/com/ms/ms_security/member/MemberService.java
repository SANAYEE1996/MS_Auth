package com.ms.ms_security.member;

import com.ms.ms_security.member.Member;
import com.ms.ms_security.member.Role;
import com.ms.ms_security.jwt.JwtTokenProvider;
import com.ms.ms_security.jwt.TokenInfo;
import com.ms.ms_security.member.MemberRepository;
import com.ms.ms_security.member.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

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
                        return Mono.just(new loginCheck(user.getId(), username, user.getPassword()));
                    }
                    log.info("password is not correct {}", rawPassword);
                    return Mono.error(new RuntimeException("password is not correct"));
                })
                .flatMap(user -> Mono.zip(Mono.just(user), roleRepository.findByMemberId(user.getId()).collectList())
                        .flatMap(req -> Mono.just(new UsernamePasswordAuthenticationToken(req.getT1().getName(), req.getT1().getPassword(), req.getT2().stream().map(Role::getRole).toList().stream().map(SimpleGrantedAuthority::new).toList()))))
                .map(jwtTokenProvider::generateToken);
    }

    public Mono<String> saveMember(String email, String password, String name, String role){
        return memberEmailExists(email)
                .then(Mono.defer(() -> memberRepository.save(new Member(null, email, passwordEncoder.encode(password), name))))
                .flatMap(req -> roleRepository.save(new Role(null, req.getId(), role)))
                .then(Mono.just("member save success"));
    }

    public Mono<String> updateMember(Long id, String name, String password){
        return memberRepository.findById(id).flatMap(member -> {
            if(member == null){
                return Mono.error(new RuntimeException(id+" is exist id !"));
            }
            return memberRepository.save(new Member(id, member.getEmail(), passwordEncoder.encode(password), name));
        }).then(Mono.just("member update success"));
    }

    public Mono<Member> getMemberInfo(Long id){
        return memberRepository.findById(id).flatMap(member -> {
            if(member == null){
                return Mono.error(new RuntimeException(id+" is exist id !"));
            }
            return Mono.just(member);
        });
    }

    public Mono<String> deleteMember(Long id){
        return memberRepository.findById(id).flatMap(member -> {
            if(member == null){
                return Mono.error(new RuntimeException(id+" is exist id !"));
            }
            return memberRepository.deleteById(id);
        }).then(Mono.just("member delete success"));
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class loginCheck{
        private Long id;
        private String name;
        private String password;
    }

}

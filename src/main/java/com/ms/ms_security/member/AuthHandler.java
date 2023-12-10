package com.ms.ms_security.member;

import com.ms.ms_security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component("authHandler")
@RequiredArgsConstructor
public class AuthHandler {

    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    public Mono<ServerResponse> login(ServerRequest request){
        return request.bodyToMono(LoginDto.class)
                .flatMap(req -> memberService.login(req.getEmail(), req.getPassword()))
                .flatMap(tokenInfo -> ServerResponse.ok().bodyValue(tokenInfo))
                .onErrorResume(req -> ServerResponse.badRequest().bodyValue(req.getMessage()));
    }

    public Mono<ServerResponse> join(ServerRequest request){
        return request.bodyToMono(JoinDto.class)
                .flatMap(req -> memberService.saveMember(req.getEmail(), req.getPassword(), req.getName(), req.getRole()))
                .flatMap(result -> ServerResponse.ok().bodyValue(result));
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        return request.bodyToMono(MemberDto.class)
                .flatMap(req -> memberService.deleteMember(req.getId()))
                .flatMap(result -> ServerResponse.ok().bodyValue(result));
    }

    public Mono<ServerResponse> update(ServerRequest request){
        return request.bodyToMono(MemberDto.class)
                .flatMap(req -> memberService.updateMember(req.getId(), req.getName(), req.getPassword()))
                .flatMap(result -> ServerResponse.ok().bodyValue(result));
    }

    public Mono<ServerResponse> check(ServerRequest request){
        List<String> authList = request.headers().header("Authorization");
        if(authList.isEmpty()){
            return ServerResponse.badRequest().bodyValue(new MemberInfoDto());
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(authList.get(0).substring(7));
        return memberService.getMemberInfo(authentication.getName())
                .flatMap(req -> ServerResponse.ok().bodyValue(new MemberInfoDto(req.getId(), req.getEmail(), req.getName())));
    }
}

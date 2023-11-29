package com.ms.ms_security.reactive;

import com.ms.ms_security.dto.JoinDto;
import com.ms.ms_security.dto.LoginDto;
import com.ms.ms_security.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component("authHandler")
@RequiredArgsConstructor
public class AuthHandler {

    private final MemberService memberService;

    public Mono<ServerResponse> login(ServerRequest request){
        return request.bodyToMono(LoginDto.class)
                .flatMap(req -> memberService.login(req.getEmail(), req.getPassword()))
                .flatMap(tokenInfo -> ServerResponse.ok().bodyValue(tokenInfo));
    }

    public Mono<ServerResponse> join(ServerRequest request){
        return request.bodyToMono(JoinDto.class)
                .flatMap(req -> memberService.saveMember(req.getEmail(), req.getPassword(), req.getName()))
                .flatMap(result -> ServerResponse.ok().bodyValue(result));
    }
}

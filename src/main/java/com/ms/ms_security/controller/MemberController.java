package com.ms.ms_security.controller;


import com.ms.ms_security.dto.MemberLoginDto;
import com.ms.ms_security.jwt.TokenInfo;
import com.ms.ms_security.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginDto memberLoginDto){
        return memberService.login(memberLoginDto.getMemberEmail(), memberLoginDto.getMemberPassword());
    }

    @GetMapping("/test")
    public String test() {
        return "success";
    }
}

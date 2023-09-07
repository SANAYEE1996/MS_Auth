package com.ms.ms_security.controller;


import com.ms.ms_security.dto.MemberCreateDto;
import com.ms.ms_security.dto.MemberLoginDto;
import com.ms.ms_security.jwt.TokenInfo;
import com.ms.ms_security.service.MemberService;
import com.ms.ms_security.util.ResponseBody;
import com.ms.ms_security.util.ResponseDto;
import jakarta.validation.Valid;
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
    public TokenInfo login(@RequestBody @Valid MemberLoginDto memberLoginDto){
        try {
            return memberService.login(memberLoginDto.getMemberEmail(), memberLoginDto.getMemberPassword());
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return TokenInfo.builder().message(e.getMessage()).build();
        }
    }

    @PostMapping("/join")
    public ResponseDto join(@RequestBody @Valid MemberCreateDto memberCreateDto){
        try {
            memberService.saveMember(memberCreateDto.getEmail(), memberCreateDto.getPassword(), memberCreateDto.getName());
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(404).body(new ResponseBody<>("Join Fail")).message(e.getMessage()).build();
        }
        return ResponseDto.builder().code(200).body(new ResponseBody<>("Join Success")).message("ok").build();
    }
}

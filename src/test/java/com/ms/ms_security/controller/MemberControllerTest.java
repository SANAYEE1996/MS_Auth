package com.ms.ms_security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.ms_security.dto.MemberCreateDto;
import com.ms.ms_security.dto.MemberLoginDto;
import com.ms.ms_security.service.MemberDetailService;
import com.ms.ms_security.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberDetailService memberDetailService;

    @Autowired
    private MemberService memberService;


    @DisplayName("member join test")
    @Test
    public void joinTest() throws Exception{
        MemberCreateDto memberCreateDto = new MemberCreateDto("dudtkd0219@gmail.com", "1234", "박영상");

        String content = objectMapper.writeValueAsString(memberCreateDto);

        mockMvc.perform(post("/member/join").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인 테스트")
    @Test
    void LoginTest() throws Exception {

        String content = objectMapper.writeValueAsString(new MemberLoginDto("dudtkd0219@gmail.com","1234"));

        mockMvc.perform(post("/member/login").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}

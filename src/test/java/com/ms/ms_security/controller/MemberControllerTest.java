package com.ms.ms_security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.ms_security.member.JoinDto;
import com.ms.ms_security.member.LoginDto;
import com.ms.ms_security.member.MemberService;
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
    private MemberService memberService;


    @DisplayName("member join test")
    @Test
    public void joinTest() throws Exception{
        JoinDto joinDto = new JoinDto("dudtkd0219@gmail.com", "1234", "박영상");

        String content = objectMapper.writeValueAsString(joinDto);

        mockMvc.perform(post("/member/join").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인 테스트")
    @Test
    void LoginTest() throws Exception {

        String content = objectMapper.writeValueAsString(new LoginDto("dudtkd0219@gmail.com","1234"));

        mockMvc.perform(post("/member/login").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}

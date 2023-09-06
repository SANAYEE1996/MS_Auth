package com.ms.ms_security.service;

import com.ms.ms_security.entity.CustomerUserDetails;
import com.ms.ms_security.entity.Member;
import com.ms.ms_security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername :: {}", username);
        return createUserDetails(memberRepository.findByMemberEmail(username).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다.")));
    }

    private UserDetails createUserDetails(Member member) {
        return new CustomerUserDetails(member);
    }

}

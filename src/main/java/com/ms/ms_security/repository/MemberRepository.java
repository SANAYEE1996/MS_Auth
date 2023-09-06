package com.ms.ms_security.repository;

import com.ms.ms_security.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberEmail(String email);

    Boolean existsByMemberEmail(String email);
}

package com.ms.ms_security.repository;

import com.ms.ms_security.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberEmail(String email);

    Boolean existsByMemberEmail(String email);

    @Modifying
    @Transactional
    @Query("update member set memberName = :name, memberPassword = :password where id = :id")
    void updateMemberInformation(String name, String password, Long id);

    @Modifying
    @Transactional
    @Query("delete member where id = :id")
    void deleteMemberInformation(Long id);
}

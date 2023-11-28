package com.ms.ms_security.repository;

import com.ms.ms_security.entity.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {

    Mono<Member> findByEmail(String email);

}

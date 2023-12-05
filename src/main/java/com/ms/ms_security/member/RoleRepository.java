package com.ms.ms_security.member;

import com.ms.ms_security.member.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {

    Flux<Role> findByMemberId(Long memberId);
}

package com.ms.ms_security.repository;

import com.ms.ms_security.entity.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {

    Flux<Role> findByMemberId(Long memberId);
}

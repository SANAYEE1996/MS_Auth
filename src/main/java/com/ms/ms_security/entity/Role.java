package com.ms.ms_security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private Long id;

    @Column("member_id")
    private Long memberId;

    private String role;
}

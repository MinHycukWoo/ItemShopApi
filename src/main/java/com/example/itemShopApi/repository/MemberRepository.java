package com.example.itemShopApi.repository;

import com.example.itemShopApi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //스프링 Data JPA 사용

    Optional<Member> findByEmail (String email);
}

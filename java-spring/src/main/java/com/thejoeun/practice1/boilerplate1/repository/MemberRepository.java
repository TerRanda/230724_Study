package com.thejoeun.practice1.boilerplate1.repository;


import com.thejoeun.practice1.boilerplate1.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Member> findByEmailAndProvider(String email, String provider);
}

package com.thejoeun.practice1.boilerplate1.jpa;


import com.thejoeun.practice1.boilerplate1.model.Member;
import com.thejoeun.practice1.boilerplate1.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("mybatis TEST")
    @Test
    void jpaTest1() {
       Member member = memberRepository.findById(10L).get();
       assertThat(member.getEmail()).isEqualTo("Faker@thejoeun.com");
    }
}

package com.thejoeun.practice1.boilerplate1.mybatis;

import com.thejoeun.practice1.boilerplate1.model.Member;
import com.thejoeun.practice1.boilerplate1.repository.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MybatisTest {
    @Autowired
    private MemberMapper memberMapper;

    @DisplayName("mybatis 사용 가능 여부 테스트")
    @Test
    void mybatisTest1() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", 2L);

        Member member = memberMapper.selectMember((HashMap<String, Object>) paramMap);
        System.out.println("member 2 email: " + member.getEmail());
    }
}

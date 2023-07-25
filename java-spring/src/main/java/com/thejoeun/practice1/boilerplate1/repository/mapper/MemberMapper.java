package com.thejoeun.practice1.boilerplate1.repository.mapper;

import com.thejoeun.practice1.boilerplate1.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemberMapper {
    Member selectMember(HashMap<String, Object> map);

    List<Member> selectMemberList(HashMap<String, Object> map);
}

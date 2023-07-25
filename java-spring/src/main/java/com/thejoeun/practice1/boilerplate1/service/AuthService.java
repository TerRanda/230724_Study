package com.thejoeun.practice1.boilerplate1.service;


import com.thejoeun.practice1.boilerplate1.config.jwt.JwtTokenProvider;
import com.thejoeun.practice1.boilerplate1.model.Member;
import com.thejoeun.practice1.boilerplate1.model.dto.JwtTokenDto;
import com.thejoeun.practice1.boilerplate1.model.dto.MemberRequestDto;
import com.thejoeun.practice1.boilerplate1.model.dto.MemberResponseDto;
import com.thejoeun.practice1.boilerplate1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    //WebSecurityConfig에 @Bean PasswordEncoder 생성하니까 Error creating bean with name 'authController', 'authService' 오류해결.
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    //Provider: 인증정보 관리빌더.

    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member)); //멤버저장
    }

    public JwtTokenDto login(MemberRequestDto requestDto) {
        //로그인 부분 핵심: 토큰 던져주기.
        //UsernamePasswordAuthenticationToken 이메일이랑 패스워드가 맞으면 통과시켜주는 역할.
        //Token = requestDto의 toAuthentication .
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        //authenticate : 정보가 같은지 안같은지 인증해줌.
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        //인증된 정보인 authentication을 Provider에 넣어주면 토큰값을 줌.
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateTokenDto(authentication);

        Optional<Member> optMember = memberRepository.findByEmail(requestDto.getEmail());
        Member member = optMember.isPresent() ? optMember.get() : null;
        if (Objects.nonNull(member)) {
            member.setAccessToken(jwtTokenDto.getAccessToken());
            member.setAccessTokenExpireIn(jwtTokenDto.getTokenExpiresIn());
            memberRepository.save(member);
        }

        return jwtTokenDto;
    }
}
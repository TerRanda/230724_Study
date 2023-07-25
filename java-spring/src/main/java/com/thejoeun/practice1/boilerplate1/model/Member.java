package com.thejoeun.practice1.boilerplate1.model;

import com.thejoeun.practice1.boilerplate1.model.enums.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.Mapper;

@Entity
@Mapper
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    //    @Column(nullable = false)
    private String password;

    //    @Column(nullable = false)
    private String nickname;

    private String provider;

    @Column(length = 2000)
    private String accessToken;

    private Long accessTokenExpireIn;

    @Enumerated(EnumType.STRING)
    private Authority authority;

//    @Builder
//    public Member(Long id, String email, String password, String nickname, Authority authority) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.nickname = nickname;
//        this.authority = authority;
//    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) { this.password = password; }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessTokenExpireIn(Long accessTokenExpireIn) {
        this.accessTokenExpireIn = accessTokenExpireIn;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Member update(
            String nickname,
            String email
    ) {
        this.nickname = nickname;
        this.email = email;
        return this;
    }

    public Member update(
//            String name,
            String accessToken,
            Long accessTokenExpireIn,
            String email
    ) {
//        this.name = name;
        this.accessToken = accessToken;
        this.accessTokenExpireIn = accessTokenExpireIn;
        this.email = email;
        return this;
    }
}

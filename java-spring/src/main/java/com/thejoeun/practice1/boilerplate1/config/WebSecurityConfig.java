package com.thejoeun.practice1.boilerplate1.config;

import com.thejoeun.practice1.boilerplate1.config.jwt.JwtAccessDeniedHandler;
import com.thejoeun.practice1.boilerplate1.config.jwt.JwtAuthenticationEntryPoint;
import com.thejoeun.practice1.boilerplate1.config.jwt.JwtSecurityConfig;
import com.thejoeun.practice1.boilerplate1.config.jwt.JwtTokenProvider;
import com.thejoeun.practice1.boilerplate1.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.thejoeun.practice1.boilerplate1.config.oauth.OAuth2CustomAuthenticationSuccessHandler;
import com.thejoeun.practice1.boilerplate1.config.oauth.OAuth2CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2CustomUserService oAuth2CustomUserService;
//    private final MemberService memberService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
//                .antMatchers("/img/**", "/css/**", "/js/**")
//                .requestMatchers("/img/**", "/css/**", "/js/**", "/auth/**")
                ;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
//            , HandlerMappingIntrospector introspector
    ) throws Exception {
//        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()

                .authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/auth/**")).permitAll()
//                .antMatchers("/question/detail/**")
//                .authorizeHttpRequests((authorize) -> authorize
//                    .requestMatchers("/auth/**").permitAll()
//                    .anyRequest().authenticated())
//            .authorizeRequests()
////                .antMatchers("/auth/**").permitAll()
//            .requestMatchers("/auth/**").permitAll()
////                .requestMatchers(mvcMatcherBuilder.pattern("/auth/**")).permitAll()
//                .requestMatchers(AntPathRequestMatcher.antMatcher("/auth/**"))
//                .requestMatchers(new AntPathRequestMatcher("/auth/**"))
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(customAuth2SuccessHandler())
                .userInfoEndpoint() // OAuth 2.0 Provider로부터 사용자 정보를 가져오는 엔드포인트를 지정하는 메서드
                .userService(oAuth2CustomUserService)   // OAuth 2.0 인증이 처리되는데 사용될 사용자 서비스를 지정하는 메서드
        ;
        http.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }

    @Bean
    public OAuth2CustomAuthenticationSuccessHandler customAuth2SuccessHandler() {
        return new OAuth2CustomAuthenticationSuccessHandler(oAuth2CustomUserService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

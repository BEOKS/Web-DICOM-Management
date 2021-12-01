package com.knuipalab.dsmp.config.auth;

import com.knuipalab.dsmp.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable().headers().frameOptions().disable()//h2-console 화면을 사용하기 위한 옵션
                .and()
                    .authorizeRequests()//URL별 권한 관리 옵션 설정 시작
                    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll() //전체 접근 가능
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())//USER 권한을 가진 사람만 접근 가능
                    .anyRequest().authenticated()//이외의 URL은 인증된 사람만 접근 가능
                .and()
                    .logout()
                        .logoutSuccessUrl("/")//로그아웃 성공시 리다이렉션
                .and()
                    .oauth2Login()//oauth 로그인 기능 설정 시작
                        .userInfoEndpoint() //로그인 성공 후 사용자 정보를 가져올때 설정 담당
                            .userService(customOAuth2UserService); //로그인 성공시 후속조치를 위한 서비스 인터페이스 구현체 등록
    }
}

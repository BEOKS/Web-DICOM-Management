package com.knuipalab.dsmp.configuration.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 커스텀한 OAuth2UserService DI
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적인 리소스들에 대해서 시큐리티 적용 무시.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 스프링 시큐리티는 기본적으로 crsf에 대해 체크하기에 post가 안된다. 그래서 이를 disable 시켜야함
        http.authorizeRequests()
                .antMatchers("/","/oauth2/*").permitAll() // root page, login page는 모두에게 허용
                .anyRequest().authenticated() // 나머지 page는 인증된 사용자만 허용
            .and()
                .logout().logoutSuccessUrl("/redirectToMainPage") // 로그아웃에 대해서 성공하면 "/"로 이동
            .and()
                .oauth2Login()
                .defaultSuccessUrl("/redirectToMainPage") // oauth2 로그인 성공 시 "/"로 이동
                .userInfoEndpoint()
                .userService(customOAuth2UserService); // oauth2 로그인에 성공하면, 유저 데이터를 가지고 우리가 생성한 customOAuth2UserService에서 처리를 하겠다. 그 후 "/"로 이동.
    }

}

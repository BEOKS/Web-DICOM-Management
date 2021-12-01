package com.knuipalab.dsmp.config.auth;

import com.knuipalab.dsmp.config.auth.dto.OAuthAttributes;
import com.knuipalab.dsmp.config.auth.dto.SessionUser;
import com.knuipalab.dsmp.domain.user.User;
import com.knuipalab.dsmp.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * CustomOAuth2UserService
 * 로그인 이후 가져온 사용자 정보를 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원
 *
 * http:{host}/oauth2/authorization/google 를 호출할 경우 로그인을 진행할 수 있다.
 * 로그인이 진행되면 데이터베이스에 정보가 저장된다.
 * http:{host}/logout을 호출 할 경우 로그아웃이 진행된다.
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        //로그인 진행 중인 서비스 구분 코드, 네이버 구글 등
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // oauth2 로그인 진행 시 키가 되는 필드 값
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        //oauth2UserService를 통해서 가져온 유저 정보를 저장
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        //유저 정보를 데이터베이스에 업데이트
        User user = saveOrUpdate(attributes);
        //세션에 유저 정보를 SessionUser Dto 형태로 저장
        //user를 바로 저장하지 않고 SessionUser Dto를 거치는 이유는 직렬화로 인한 성능 이슈가 발생 가능하기 때문
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
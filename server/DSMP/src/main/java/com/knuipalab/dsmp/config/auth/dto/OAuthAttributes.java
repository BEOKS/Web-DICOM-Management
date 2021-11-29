package com.knuipalab.dsmp.config.auth.dto;

import com.knuipalab.dsmp.domain.user.Role;
import com.knuipalab.dsmp.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 *
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /**
     *
     * @param registrationId
     * @param userNameAttributeName
     * @param attributes : oAuth2User에서 반환하는 정보는 맵이므로 하나하나를 변환해야한다.
     * @return : 생성된 사용자 정보
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    /**
     * 빌더르 통해서 입력받은 맵을 인스턴스에 대입하여 사용자 정보를 생성한다.
     * @param userNameAttributeName
     * @param attributes
     * @return : 생성된 사용자 정보
     */
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * 현재 클래스에 저장된 사용자 정보를 User 엔티티로 변환한다.
     * 현재 가입시 기본 권한은 GUEST로 설정되어 있다.
     * @return : 변환된 User 클래스
     */
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
package com.knuipalab.dsmp.domain.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter //lombok , 모든 field 값의 getter 매소드를 자동 생성
@NoArgsConstructor // lombok , 기본 생성자 자동추가
@Document(collection = "User") //DB에 저장될 document의 이름은 @Documemt 애노테이션을 통하여 지정
public class User {

    @Id
    private String userId;

    private String name;

    private String email;

    private String picture;

    private String role = "ROLE_USER";

    @Builder
    public User(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this; }
}

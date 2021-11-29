package com.knuipalab.dsmp.domain.user;

import com.knuipalab.dsmp.domain.BaseTimeEntitiy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User extends BaseTimeEntitiy {
    @Id
    private String id;
    private String name;
    private String email;
    private String picture;
    private Role role;

    public User(String name,String email, String picture,Role role){
        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role=role;
    }
    public User update(String name,String picture){
        this.name=name;
        this.picture=picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

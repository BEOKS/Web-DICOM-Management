package com.knuipalab.dsmp.user.dto;

import com.knuipalab.dsmp.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String userId;

    private String name;

    private String email;

    private String picture;

    public UserResponseDto(User entity) {
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
    }
}

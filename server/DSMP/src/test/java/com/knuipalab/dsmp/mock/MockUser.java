package com.knuipalab.dsmp.mock;

import com.knuipalab.dsmp.domain.user.User;
import lombok.Getter;

@Getter
public class MockUser {

    User mockedUser;

    public MockUser(){
        this.mockedUser = getMockUser();
    }

    private User getMockUser(){
        return User.builder()
                .name("mockUser")
                .email("mockUser@mockUser.com")
                .picture("mockUserImg")
                .build();
    }
}

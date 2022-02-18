package com.knuipalab.dsmp.service.user;

import com.knuipalab.dsmp.domain.user.User;
import com.knuipalab.dsmp.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 email 값을 가진 사용자가 정보가 없습니다."));
    }
}

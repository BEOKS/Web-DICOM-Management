package com.knuipalab.dsmp.user.service;

import com.knuipalab.dsmp.user.domain.User;
import com.knuipalab.dsmp.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}

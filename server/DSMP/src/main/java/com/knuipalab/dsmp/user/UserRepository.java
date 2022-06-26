package com.knuipalab.dsmp.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmail(String email); // email로 신규 가입인지 아닌지 확인
}

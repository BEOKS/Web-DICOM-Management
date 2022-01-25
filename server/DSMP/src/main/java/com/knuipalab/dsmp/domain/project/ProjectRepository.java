package com.knuipalab.dsmp.domain.project;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project,String> {
    List<Project> findByUserId(String userId);
}

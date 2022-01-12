package com.knuipalab.dsmp.domain.metadata;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MetaDataRepository extends MongoRepository<MetaData, String> {
    List<MetaData> findByProjectId(String projectId);
}

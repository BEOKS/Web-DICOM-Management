package com.knuipalab.dsmp.domain.metadata;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaDataRepository extends MongoRepository<MetaData, String> {

    List<MetaData> findByProjectId(String projectId);
    Long deleteAllByProjectId(String projectId);
}

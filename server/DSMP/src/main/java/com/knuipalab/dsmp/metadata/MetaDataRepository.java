package com.knuipalab.dsmp.metadata;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaDataRepository extends MongoRepository<MetaData, String>, CustomizedMetaDataRepository {

    List<MetaData> findByProjectId(String projectId);
    Long deleteAllByProjectId(String projectId);
}

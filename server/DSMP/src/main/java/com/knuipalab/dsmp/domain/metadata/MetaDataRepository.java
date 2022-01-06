package com.knuipalab.dsmp.domain.metadata;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetaDataRepository extends MongoRepository<MetaData, String> {
}

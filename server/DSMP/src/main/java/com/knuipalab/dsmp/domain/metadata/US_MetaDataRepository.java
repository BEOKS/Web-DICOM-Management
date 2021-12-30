package com.knuipalab.dsmp.domain.metadata;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface US_MetaDataRepository extends MongoRepository<US_MetaData, String> {
}

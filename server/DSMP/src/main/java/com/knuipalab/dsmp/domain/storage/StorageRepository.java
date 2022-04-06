package com.knuipalab.dsmp.domain.storage;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StorageRepository extends MongoRepository<Storage,String> {
}

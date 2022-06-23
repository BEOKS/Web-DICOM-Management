package com.knuipalab.dsmp.storage;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StorageRepository extends MongoRepository<Storage,String> {
}

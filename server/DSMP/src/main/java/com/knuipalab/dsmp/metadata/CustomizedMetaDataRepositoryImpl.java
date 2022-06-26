package com.knuipalab.dsmp.metadata;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.util.HashMap;

public class CustomizedMetaDataRepositoryImpl implements CustomizedMetaDataRepository{

    private final MongoTemplate mongoTemplate;

    public CustomizedMetaDataRepositoryImpl(MongoTemplate mongoTemplate) {

        Assert.notNull(mongoTemplate, "MongoTemplate must not be null!");
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateType(String metadataId, String type) {
        Query query = new Query(Criteria.where("_id").is(metadataId));
        Update update = new Update();
        update.set("body.type", type);
        mongoTemplate.updateFirst(query,update,"metadata");
    }

    @Override
    public void setMalignancyClassification(String metadataId, HashMap classificationSet) {
        Query query = new Query(Criteria.where("_id").is(metadataId));
        Update update = new Update();
        classificationSet.keySet().iterator().forEachRemaining(key -> {
            update.set(String.format("%s.%s","body",key), classificationSet.get(key).toString());
        });
        mongoTemplate.updateFirst(query,update,"metadata");
    }
}

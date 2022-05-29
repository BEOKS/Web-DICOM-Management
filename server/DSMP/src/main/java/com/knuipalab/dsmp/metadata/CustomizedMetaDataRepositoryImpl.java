package com.knuipalab.dsmp.metadata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.Assert;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    //        Iterator<String> keys = parmMap.keySet().iterator();
    //        while (keys.hasNext()){
    //            String key = keys.next();
    //            System.out.println(key);
    //        }


    @Override
    public Page<MetaData> findByProjectIdWithPaging(String projectId,int page, int size, HashMap parmMap) {

        Pageable pageable = PageRequest.of(page,size, Sort.unsorted());

        Query query = new Query()
                .with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber()) // offset
                .limit(pageable.getPageSize());

        //Add Filtered
        query.addCriteria(Criteria.where("projectId").is(projectId));

        List<MetaData> filteredMetaData = mongoTemplate.find(query, MetaData.class, "metadata");
        Page<MetaData> metaDataPage = PageableExecutionUtils.getPage(
                filteredMetaData,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1),MetaData.class)
                // query.skip(-1).limit(-1)의 이유는 현재 쿼리가 페이징 하려고 하는 offset 까지만 보기에 이를 맨 처음부터 끝까지러 set 해줘 정확한 도큐먼트 개수를 구한다.
        );

        return metaDataPage;
    }


}

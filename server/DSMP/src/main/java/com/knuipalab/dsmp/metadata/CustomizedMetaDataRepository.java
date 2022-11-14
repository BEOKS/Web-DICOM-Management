package com.knuipalab.dsmp.metadata;

import org.springframework.data.domain.Page;

import java.util.HashMap;

public interface CustomizedMetaDataRepository {
    void updateType(String metadataId, String type);
    void setMalignancyClassification(String metadataId, HashMap classificationSet);
    Page<MetaData> findByProjectIdWithPagingAndFiltering(String projectId, int page, int size, HashMap parmMap);
}

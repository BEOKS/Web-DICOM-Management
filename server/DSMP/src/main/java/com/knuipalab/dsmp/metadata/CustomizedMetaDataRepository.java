package com.knuipalab.dsmp.metadata;

import java.util.HashMap;

public interface CustomizedMetaDataRepository {
    void updateType(String metadataId, String type);
    void setMalignancyClassification(String metadataId, HashMap classificationSet);
    void findByProjectIdWithPagingAndFiltering(String projectId,int page,int size);
}

package com.knuipalab.dsmp.domain.metadata;

import java.util.HashMap;

public interface CustomizedMetaDataRepository {
    void updateType(String metadataId, String type);
    void setMalignancyClassification(String metadataId, HashMap classificationSet);
}

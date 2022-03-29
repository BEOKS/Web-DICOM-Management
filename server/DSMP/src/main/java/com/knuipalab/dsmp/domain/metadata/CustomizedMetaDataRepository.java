package com.knuipalab.dsmp.domain.metadata;

public interface CustomizedMetaDataRepository {
    void updateType(String metadataId, String type);
    public void updateClassification(String metadataId, String Classification);
}

package com.knuipalab.dsmp.metadata;

import lombok.Getter;
import org.bson.conversions.Bson;

@Getter
public class MetaDataResponseDto {

    private String metadataId;

    private String projectId;

    private Bson body;

    public MetaDataResponseDto(MetaData entity){
        this.metadataId = entity.getMetadataId();
        this.projectId = entity.getProjectId();
        this.body = entity.getBody();
    }
}

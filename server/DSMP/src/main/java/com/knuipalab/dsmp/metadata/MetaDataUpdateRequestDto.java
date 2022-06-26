package com.knuipalab.dsmp.metadata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;

@Setter
@Getter
@NoArgsConstructor
public class MetaDataUpdateRequestDto {

    private String metadataId;
    private Document body;

    public MetaDataUpdateRequestDto(String metadataId,Document body){
        this.metadataId = metadataId;
        this.body = body;
    }
}



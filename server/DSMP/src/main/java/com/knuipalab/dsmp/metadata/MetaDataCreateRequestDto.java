package com.knuipalab.dsmp.metadata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;

@Setter
@Getter
@NoArgsConstructor
public class MetaDataCreateRequestDto {

    private String projectId;

    private Document body;

    public MetaDataCreateRequestDto(String projectId, Document body){
        this.projectId = projectId;
        this.body = body;
    }
}

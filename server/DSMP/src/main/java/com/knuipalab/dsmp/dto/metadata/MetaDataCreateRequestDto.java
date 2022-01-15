package com.knuipalab.dsmp.dto.metadata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;

@Setter
@Getter
@NoArgsConstructor
public class MetaDataCreateRequestDto {

    private String projectId;

    private Bson body;

    public MetaDataCreateRequestDto(String projectId, String strBody){
        this.projectId = projectId;
        Document body = Document.parse(strBody);
        this.body = body;
    }
}

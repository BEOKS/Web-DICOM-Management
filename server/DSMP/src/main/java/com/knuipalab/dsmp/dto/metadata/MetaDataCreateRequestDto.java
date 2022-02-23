package com.knuipalab.dsmp.dto.metadata;

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

    public MetaDataCreateRequestDto(String projectId, String strBody){
        this.projectId = projectId;
        Document body = Document.parse(strBody);
        this.body = body;
    }
}

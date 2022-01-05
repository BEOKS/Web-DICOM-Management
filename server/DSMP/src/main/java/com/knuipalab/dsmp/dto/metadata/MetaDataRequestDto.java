package com.knuipalab.dsmp.dto.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;

@Getter
public class MetaDataRequestDto {

    private Bson body;

    public MetaDataRequestDto(String strBody){
        Document body = Document.parse(strBody);
        this.body = body;
    }
}

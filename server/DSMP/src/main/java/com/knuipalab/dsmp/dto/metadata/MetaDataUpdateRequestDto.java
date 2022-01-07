package com.knuipalab.dsmp.dto.metadata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;

@Setter
@Getter
@NoArgsConstructor
public class MetaDataUpdateRequestDto {

    private Bson body;

    public MetaDataUpdateRequestDto(String strBody){
        Document body = Document.parse(strBody);
        this.body = body;
    }
}



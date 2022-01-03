package com.knuipalab.dsmp.dto.metadata;

import com.knuipalab.dsmp.domain.metadata.MetaData;
import lombok.Getter;
import org.bson.conversions.Bson;
import org.bson.json.JsonObject;

@Getter
public class MetaDataResponseDto {

    private String _id;

    private Bson body;

    public MetaDataResponseDto(MetaData entity){
        this._id = entity.get_id();
        this.body = entity.getBody();
    }
}

package com.knuipalab.dsmp.dto.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MetaDataCreateAllRequestDto {

    private String projectId;
    private List<Document> bodyList;

    public MetaDataCreateAllRequestDto(String projectId, String strBody){
        this.projectId = projectId;
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Document> bsonList = mapper.readValue(strBody, new TypeReference<List<Document>>(){});
            this.bodyList = bsonList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

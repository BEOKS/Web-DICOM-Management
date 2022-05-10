package com.knuipalab.dsmp.metadata.dto;


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

    public MetaDataCreateAllRequestDto(String projectId, List<Document> bodyList){
        this.projectId = projectId;
        this.bodyList = bodyList;
    }

}

package com.knuipalab.dsmp.metadata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MetaDataDeleteAllRequestDto {

    String projectId;
    List<String> metadataIdList;

    public MetaDataDeleteAllRequestDto(String projectId, List<String> metadataIdList) {
        this.projectId = projectId;
        this.metadataIdList = metadataIdList;
    }
}

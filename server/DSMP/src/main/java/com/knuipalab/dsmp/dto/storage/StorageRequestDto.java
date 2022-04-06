package com.knuipalab.dsmp.dto.storage;

import com.knuipalab.dsmp.domain.storage.Storage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StorageRequestDto {

    private String projectId;

    private String filePath;

    private String fileOriginalName;

    @Builder
    StorageRequestDto(String projectId,String fileOriginalName){
        this.projectId = projectId;
        this.fileOriginalName = fileOriginalName;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    private Storage toEntity(){
        return Storage.builder()
                .fileOriginalName(fileOriginalName)
                .projectId(projectId)
                .filePath(filePath)
                .build();
    }

    @Override
    public String toString() {
        return "StorageRequestDto{" +
                "projectId='" + projectId + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileOriginalName='" + fileOriginalName + '\'' +
                '}';
    }
}

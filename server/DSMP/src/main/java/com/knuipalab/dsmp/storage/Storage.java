package com.knuipalab.dsmp.storage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "storage")
public class Storage {
    @Id
    private String fileId;

    private String projectId;

    private String filePath;

    private String fileOriginalName;

    @Builder
    public Storage(String fileId,String projectId,String filePath,String fileOriginalName){
        this.fileId = fileId;
        this.projectId = projectId;
        this.filePath = filePath;
        this.fileOriginalName = fileOriginalName;
    }

}

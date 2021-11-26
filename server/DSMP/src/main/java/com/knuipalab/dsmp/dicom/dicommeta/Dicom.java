package com.knuipalab.dsmp.dicom.dicommeta;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document("MetaData")
public class Dicom {
    @Id
    private String id;
    private String patientUID;

    @Builder
    public Dicom(String patientUID){
        this.patientUID=patientUID;
    }
}

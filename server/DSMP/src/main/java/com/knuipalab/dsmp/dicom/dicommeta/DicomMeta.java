package com.knuipalab.dsmp.dicom.dicommeta;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Document("MetaData")
public class DicomMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(length = 100,nullable = false)
    private String patientUID;

    @Builder
    public DicomMeta(String patientUID){
        this.patientUID=patientUID;
    }
}

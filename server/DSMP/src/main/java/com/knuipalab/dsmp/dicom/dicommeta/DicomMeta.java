package com.knuipalab.dsmp.dicom.dicommeta;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class DicomMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100,nullable = false)
    private String patientUID;

    @Builder
    public DicomMeta(String patientUID){
        this.patientUID=patientUID;
    }
}

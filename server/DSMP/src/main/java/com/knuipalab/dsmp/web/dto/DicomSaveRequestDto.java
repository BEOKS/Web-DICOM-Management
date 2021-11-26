package com.knuipalab.dsmp.web.dto;

import com.knuipalab.dsmp.dicom.dicommeta.Dicom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DicomSaveRequestDto {
    private String patientUID;
    @Builder
    public DicomSaveRequestDto(String patientUID){
        this.patientUID=patientUID;
    }
    public Dicom toEntity(){
        return Dicom.builder().
                patientUID(patientUID)
                .build();
    }
}

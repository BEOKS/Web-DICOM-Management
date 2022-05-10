package com.knuipalab.dsmp.patient.dto;

import com.knuipalab.dsmp.patient.domain.Patient;
import lombok.Getter;

@Getter
public class PatientResponseDto {

    private Patient body;

    public PatientResponseDto(Patient entity){
        this.body = entity;
    }
}

package com.knuipalab.dsmp.patient;

import com.knuipalab.dsmp.patient.Patient;
import lombok.Getter;

@Getter
public class PatientResponseDto {

    private Patient body;

    public PatientResponseDto(Patient entity){
        this.body = entity;
    }
}

package com.knuipalab.dsmp.dto.patient;

import com.knuipalab.dsmp.domain.metadata.MetaData;
import com.knuipalab.dsmp.domain.patient.Patient;
import lombok.Getter;
import org.bson.conversions.Bson;

@Getter
public class PatientResponseDto {

    private Patient body;

    public PatientResponseDto(Patient entity){
        this.body = entity;
    }
}

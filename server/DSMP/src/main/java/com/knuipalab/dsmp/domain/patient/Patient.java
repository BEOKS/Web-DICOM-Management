package com.knuipalab.dsmp.domain.patient;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "patient")
public class Patient {

    @Id
    private String patientId;

    private int referencedCount;

    private String userId;

    public void addReferencedCount(){
        this.referencedCount = this.referencedCount + 1;
    }
    public void minusReferencedCount(){
        this.referencedCount = this.referencedCount - 1;
    }

    @Builder
    public Patient(String patientId,String userId){
        this.patientId = patientId;
        this.referencedCount = 0;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", projectCount=" + referencedCount +
                ", userId='" + userId + '\'' +
                '}';
    }
}

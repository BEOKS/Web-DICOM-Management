package com.knuipalab.dsmp.patient.domain;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PatientRepository extends MongoRepository<Patient,String> {
    List<Patient> findByUserId(String userId);
}

package com.knuipalab.dsmp.dicom.dicommeta;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DicomRepository extends MongoRepository<Dicom,String> {

}

package com.knuipalab.dsmp.dicom.dicommeta;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DicomRepositoryTest {
    @Autowired
    DicomRepository dicomMetaRepository;

    @AfterEach
    public void cleanup(){
        dicomMetaRepository.deleteAll();
    }

    @Test
    public void getDicomMeta(){
        String patientUIDExample="1.2.840.xxxxx.3.152.235.2.12.187636473";
        dicomMetaRepository.save(Dicom.builder()
                .patientUID(patientUIDExample)
                .build());
        List<Dicom> dicomMetaList=dicomMetaRepository.findAll();

        Dicom dicomMeta=dicomMetaList.get(0);
        assertThat(dicomMeta.getPatientUID()).isEqualTo(patientUIDExample);
    }

}

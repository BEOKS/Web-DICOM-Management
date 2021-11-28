package com.knuipalab.dsmp.web;

import com.knuipalab.dsmp.dicom.dicommeta.Dicom;
import com.knuipalab.dsmp.dicom.dicommeta.DicomRepository;
import com.knuipalab.dsmp.web.dto.DicomSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DicomAPIControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DicomRepository dicomRepository;

    @AfterEach
    public void tearDown(){
        dicomRepository.deleteAll();
    }
    @Test
    public void save_dicom(){
        String patientUIDExample="1.2.840.xxxxx.3.152.235.2.12.187636473";
        DicomSaveRequestDto dicomSaveRequestDto=DicomSaveRequestDto.builder()
                .patientUID(patientUIDExample).build();
        String url="http://localhost:"+port+"/api/v1/dicom";

        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url,dicomSaveRequestDto,String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isEqualTo(patientUIDExample);

        List<Dicom> all=dicomRepository.findAll();
        assertThat(all.get(0).getPatientUID()).isEqualTo(patientUIDExample);

    }
}

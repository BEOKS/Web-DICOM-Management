package com.knuipalab.dsmp.service.orthanc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrthancRestClient {

    private static final String GET_TOOLS_ENDPOINT_URL = "http://orthanc:8042/tools/find";
    private static final String DELETE_PATIENT_ENDPOINT_URL = "http://orthanc:8042/patients/{id}";
    private static final String UPLOAD_DICOM_ENDPOINT_URL = "http://orthanc:8042/instances";
    private static RestTemplate restTemplate = new RestTemplate();

    protected JsonNode uploadDicom(@RequestPart("dicomfile")MultipartFile file) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        // header setting
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/dicom");

        // post api call
        byte[] content = file.getBytes();
        HttpEntity<byte []> requestEntity = new HttpEntity<>(content, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(UPLOAD_DICOM_ENDPOINT_URL, requestEntity, String.class);

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        }
        catch(Exception ex){
            System.err.println("Error get Response");
            jsonNode = objectMapper.readTree(response.toString());
        }
        return jsonNode;
    }

    protected JsonNode getPatientInfo(String patientUUID) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String,Object> param = new HashMap<>();
        JsonNode query = objectMapper.readTree("{\"PatientID\":\"" + patientUUID + "\"}");
        param.put("Expand", true);
        param.put("Full", true);
        param.put("Level", "Study");
        param.put("Query", query);

        // header setting
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // post api call
        HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(GET_TOOLS_ENDPOINT_URL, requestEntity, String.class);

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        }
        catch(Exception ex){
            System.err.println("Error get Response");
            jsonNode = objectMapper.readTree(response.toString());
        }
        return jsonNode;
    }

    protected ResponseEntity deletePatient(String patientUUID) throws IOException {

        Map < String, String > params = new HashMap < String, String > ();
        params.put("id", patientUUID);
        restTemplate.delete(DELETE_PATIENT_ENDPOINT_URL, params);

        return new ResponseEntity(HttpStatus.OK);
    }
}

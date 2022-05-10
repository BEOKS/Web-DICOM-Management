package com.knuipalab.dsmp.orthanc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrthancRestClientJava {

    private static final String GET_TOOLS_ENDPOINT_URL = "http://orthanc:8042/tools/find";
    private static final String DELETE_PATIENT_ENDPOINT_URL = "http://orthanc:8042/patients/{id}";
    private static final String UPLOAD_DICOM_ENDPOINT_URL = "http://orthanc:8042/instances";

    private static final String DELETE_STUDY_ENDPOINT_URL = "http://orthanc:8042/studies/{id}";
    private static final String GET_STUDY_ENDPOINT_URL = "http://orthanc:8042/studies";
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

    protected JsonNode getStudyInfo(String studyID) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String,Object> param = new HashMap<>();
        JsonNode query = objectMapper.readTree("{\"StudyInstanceUID\":\"" + studyID + "\"}");
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

    protected JsonNode getPatientInfo(String patientID) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String,Object> param = new HashMap<>();
        JsonNode query = objectMapper.readTree("{\"PatientID\":\"" + patientID + "\"}");
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

    protected JsonNode getStudies() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate.getForEntity(GET_STUDY_ENDPOINT_URL, String.class);

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

    protected ResponseEntity deleteStudy(String studyID) throws IOException {
        Map < String, String > params = new HashMap < String, String > ();
        System.out.println(studyID);
        params.put("id", studyID);

        System.out.println(params.get(studyID));
        restTemplate.delete(DELETE_STUDY_ENDPOINT_URL, params);

        return new ResponseEntity(HttpStatus.OK);
    }
}

package com.knuipalab.dsmp.controller.dicom.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class DicomApiController {

    @PostMapping("/api/dicom")
    public JsonNode uploadDicom(@RequestPart("dicomfile") MultipartFile file) throws IOException {

        System.out.println("--------/api/dicom POST api Call------------");
        System.out.println("-----------Dicom File Info------------");
        System.out.println("File Name: " + file.getOriginalFilename());
        System.out.println("Content-type: " + file.getContentType());
        System.out.println("File Size: " + file.getSize());
        System.out.println("File: " + file.toString());

        String request_URL = "http://orthanc:8042/instances";

        RestTemplate restTemplate = new RestTemplate();

        // header setting
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/dicom");

        // post api call
        byte[] content = file.getBytes();
        HttpEntity<byte []> requestEntity = new HttpEntity<>(content, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(request_URL, requestEntity, String.class);

        System.out.println("-----------Response Result------------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        }
        catch(Exception ex){
            System.err.println("Error get Response");
            jsonNode = objectMapper.readTree(response.toString());
        }
        System.out.println(response.toString());

        return jsonNode;
    }

    @GetMapping("/api/dicom")
    public String listDicom(){
        // String request_URL = "http://localhost:8042/instances";
        String request_URL = "http://orthanc:8042/instances";

        RestTemplate restTemplate = new RestTemplate();

        // get api call
        ResponseEntity<String> response = restTemplate.getForEntity(request_URL, String.class);
        System.out.println(response.toString());

        return response.toString();
    }

    @PostMapping("/api")
    public String test(@RequestParam MultipartFile file){
        System.out.println(file.getSize());
        return "Hello ";
    }
}
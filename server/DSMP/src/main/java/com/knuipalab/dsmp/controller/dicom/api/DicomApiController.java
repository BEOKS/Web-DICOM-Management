package com.knuipalab.dsmp.controller.dicom.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    @GetMapping("/api/dicom/{id}/patient")
    public JsonNode getPatientInfo(@PathVariable String id){
//        String request_URL = "http://localhost:8042/instances/" + id + "/patient";
        String request_URL = "http://orthanc:8042/instances/" + id + "/patient";

        RestTemplate restTemplate = new RestTemplate();

        // get api call
        ResponseEntity<String> response = restTemplate.getForEntity(request_URL, String.class);
        System.out.println("-----------Response Result------------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode resultJson;
        try {
            resultJson = objectMapper.readTree(response.getBody());
        }
        catch(Exception ex){
            System.err.println("Error get Response");
            resultJson = objectMapper.nullNode();
        }
        System.out.println(response.toString());

        return resultJson;
    }

    @GetMapping("/api/patient/{id}/study")
    public JsonNode patientDicom(@PathVariable String id) throws IOException {

        System.out.println("--------/api/patient/{id}/study GET api Call------------");
        System.out.println("-----------Param Info------------");
        System.out.println("Input id: " + id);

//        String request_URL = "http://localhost:8042/tools/find";
        String request_URL = "http://orthanc:8042/tools/find";

        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();

        HashMap<String,Object> param = new HashMap<>();
        JsonNode query = objectMapper.readTree("{\"PatientID\":\"" + id + "\"}");
        param.put("Expand", true);
        param.put("Full", true);
        param.put("Level", "Study");
        param.put("Query", query);

        // header setting
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // post api call
        HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(request_URL, requestEntity, String.class);

        System.out.println("-----------Response Result------------");
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        }
        catch(Exception ex){
            System.err.println("Error get Response");
            jsonNode = objectMapper.readTree(response.toString());
        }
        System.out.println(response.toString());

        // parsing StudyInstanceUID
        ArrayNode arrayNode = (ArrayNode) jsonNode;
        List<JsonNode> list = new ArrayList<JsonNode>();

        Iterator<JsonNode> itr = arrayNode.elements();
        while( itr.hasNext() ) {
            Iterator<JsonNode> in_itr = itr.next().get("MainDicomTags").elements();
            while( in_itr.hasNext() ){
                JsonNode field = in_itr.next();
                if (field.get("Name").asText().equals("StudyInstanceUID")){
                    list.add(field.get("Value"));
                    break;
                }
            }
        }
        return objectMapper.valueToTree(list);
    }

    @PostMapping("/api")
    public String test(@RequestParam MultipartFile file){
        System.out.println(file.getSize());
        return "Hello ";
    }
}
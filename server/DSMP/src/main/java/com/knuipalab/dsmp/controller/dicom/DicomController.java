//package com.knuipalab.dsmp.controller.dicom;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RequestCallback;
//import org.springframework.web.client.ResponseExtractor;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.*;
//
//@Controller
//public class DicomController {
//    @Value("${hostLocation}")
//    private String hostLocation;
//
//    @GetMapping("/api/dicom/{id}")
//    public String downloadDicom(@PathVariable String id){
//        return "redirect:http://orthanc:8042/instances/" + id + "/file";
//    }
//
//    @GetMapping("/api/patient/{id}/dicom")
//    public String downloadPatientDicom(@PathVariable String id, HttpServletResponse httpResponse) throws IOException{
//        System.out.println("host location is "+hostLocation);
////        String request_URL = "http://localhost:8042/tools/find";
//        String request_URL = "http://orthanc:8042/tools/find";
//
//        System.out.println("call GET /api/dicom/patient/{id}");
//
//        RestTemplate restTemplate = new RestTemplate();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        HashMap<String,Object> param = new HashMap<>();
//        JsonNode query = objectMapper.readTree("{\"PatientID\":\"" + id + "\"}");
//        param.put("Expand", true);
//        param.put("Full", true);
//        param.put("Level", "Study");
//        param.put("Query", query);
//
//        // header setting
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // post api call
//        HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(param, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(request_URL, requestEntity, String.class);
//
//        System.out.println("-----------Response Result------------");
//        JsonNode jsonNode;
//        try {
//            jsonNode = objectMapper.readTree(response.getBody());
//        }
//        catch(Exception ex){
//            System.err.println("Error get Response");
//            jsonNode = objectMapper.readTree(response.toString());
//        }
//        System.out.println(response.toString());
//
//        ArrayNode arrayNode = (ArrayNode) jsonNode;
//        Iterator<JsonNode> itr = arrayNode.elements();
//        String patientId = "";
//        while( itr.hasNext() ) {
//            patientId = itr.next().get("ParentPatient").asText();
//        }
//        System.out.println(patientId);
//
//        return "redirect:http://"+hostLocation+":8042/patients/" + patientId + "/archive";
//    }
//}

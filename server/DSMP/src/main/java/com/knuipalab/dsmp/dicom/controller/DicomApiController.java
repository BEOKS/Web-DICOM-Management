package com.knuipalab.dsmp.dicom.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.knuipalab.dsmp.orthanc.service.OrthancService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class DicomApiController {

    OrthancService orthancService = new OrthancService();

    @PostMapping("/api/dicom")
    public JsonNode uploadDicom(@RequestPart("dicomfile") MultipartFile file) throws IOException {
        return orthancService.uploadDicom(file);
    }

    @GetMapping("/api/patient/{id}/study")
    public JsonNode getStudyListByPatientID(@PathVariable String id) throws IOException {
         return orthancService.getStudyListByPatientID(id);
    }

    @DeleteMapping("/api/patient/{id}")
    public ResponseEntity deletePatientByPatientID(@PathVariable String id) throws IOException {
        String patientUUID = orthancService.getPatinetUuidByPatientID(id);

        if( patientUUID == "-1" ){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        ResponseEntity response = orthancService.deletePatientByPatientUUID(patientUUID);
        return response;
    }

    @DeleteMapping("/api/study/{id}")
    public ResponseEntity deleteStudyByStudyID(@PathVariable String id) throws IOException {
        String studyUUID = orthancService.getStudyUuidByStudyID(id);

        if( studyUUID == "-1" ){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        ResponseEntity response = orthancService.deleteStudyByStudyUUID(studyUUID);
        return response;
    }

    @GetMapping("/api/study")
    public JsonNode getStudy() throws IOException {
        return orthancService.getStudies();
    }

    @GetMapping("/api")
    public String test(@RequestParam MultipartFile file){
        System.out.println(file.getSize());
        return "Welcome DSMP Service";
    }
}
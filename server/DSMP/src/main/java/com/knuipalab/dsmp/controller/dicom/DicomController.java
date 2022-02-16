package com.knuipalab.dsmp.controller.dicom;

import com.knuipalab.dsmp.service.orthanc.OrthancService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

@Controller
public class DicomController {
    @Value("${hostLocation}")
    private String hostLocation;

    OrthancService orthancService = new OrthancService();

    @GetMapping("/api/dicom/{id}")
    public String downloadDicom(@PathVariable String id){
        return "redirect:http://" + hostLocation + "/dicom/instances/" + id + "/file";
    }

    @GetMapping("/api/patient/{id}/dicom")
    public String downloadPatientDicom(@PathVariable String id) throws IOException{
        String patientUUID = orthancService.getPatinetUuidByPatientID(id);

        return "redirect:http://" + hostLocation + "/dicom/patients/" + patientUUID + "/archive";
    }

    @GetMapping("/api/study/{id}/dicom")
    public String downloadStudyDicom(@PathVariable String id) throws IOException{
        String studyUUID = orthancService.getStudyUuidByStudyID(id);
        return "redirect:http://" + hostLocation + "/dicom/studies/" + studyUUID + "/archive";
    }
}

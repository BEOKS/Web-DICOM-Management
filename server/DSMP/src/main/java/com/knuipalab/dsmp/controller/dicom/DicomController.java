package com.knuipalab.dsmp.controller.dicom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.knuipalab.dsmp.service.orthanc.OrthancRestClient;
import com.knuipalab.dsmp.service.orthanc.OrthancService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
public class DicomController {

    OrthancService orthancService = new OrthancService();

    @GetMapping("/api/dicom/{id}")
    public String downloadDicom(@PathVariable String id){
        return "redirect:http://orthanc:8042/instances/" + id + "/file";
    }

    @GetMapping("/api/patient/{id}/dicom")
    public String downloadPatientDicom(@PathVariable String id) throws IOException{
        String patientUUID = orthancService.getPatinetUuidByPatientID(id);

        return "redirect:http://localhost:8042/patients/" + patientUUID + "/archive";
    }
}

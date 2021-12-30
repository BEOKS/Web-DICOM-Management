package com.knuipalab.dsmp.controller.dicom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class DicomController {

//    {
//        ID: "",
//        ParentPatient : "",
//        ParentSeries : "",
//        ParentStudy : "",
//        Path : "",
//        Status : ""
//    }


//    @Autowired
//    private RestTemplate restTemplate;

//    @GetMapping("/api/dicom/{id}")
//    public void downloadDicom(@PathVariable String id){
//        System.out.println(id);
//        String BASE_URL = "redirect:http://localhost:8042/instances/" + id + "/file";
//        final HttpHeaders headers = new HttpHeaders();
//
//        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON}));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("KEY", "VALUE");
//
//        final HttpEntity<String> entity = new HttpEntity<String>("", headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        System.out.println("It's OK");
//        ResponseEntity<String> respEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, String.class);
//
//        System.out.println(respEntity.toString());
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(respEntity.getBody());
//        JsonNode jsonNode = objectMapper.readTree(respEntity.getBody());

//        return;
//    }
    @GetMapping("/Dicom/{id}")
    public String downloadDicom(@PathVariable String id){
        return "redirect:http://localhost:8042/instances/" + id + "/file";
    }

    @GetMapping("/forward")
    public String forward(){
        return "forward:/api";
    }

    @GetMapping("/redirect")
    public String redirect(){
        return "redirect:/api";
    }

    @ResponseBody
    @GetMapping("/api")
    public String test(){
        return "Hello ";
    }

//    @PostMapping("/api/dicom")
//    public String uploadDicom(@RequestBody("dicom")dicomfile, @RequestBody("metadata")metadata){
//         upload dicom file to dicom server
//         redirect POST localhost:8042/instances
//         get Result, we use ID column
//
//         upload metadata to mongoDB with dicom ID
//
//        return Status
//    }

}

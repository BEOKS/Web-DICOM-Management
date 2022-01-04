package com.knuipalab.dsmp.controller.dicom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DicomController {

    @GetMapping("/Dicom/{id}")
    public String downloadDicom(@PathVariable String id){
        return "redirect:http://orthanc:8042/instances/" + id + "/file";
    }
}

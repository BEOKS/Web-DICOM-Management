package com.knuiaplab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DicomController {

    @GetMapping("/api/dicom/{id}")
    public String downloadDicom(@PathVariable Long id){
        String redirect_uri = "localhost:8042/instances" + id + "/file";
        return redirect_uri;
    }
}
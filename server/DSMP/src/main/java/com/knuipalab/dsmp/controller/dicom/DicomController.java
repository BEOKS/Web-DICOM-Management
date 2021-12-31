package com.knuipalab.dsmp.controller.dicom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class DicomController {

//    @Autowired
//    private RestTemplate restTemplate;

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
    @PostMapping("/api")
    public String test(@RequestParam MultipartFile file){
        System.out.println(file.getSize());
        return "Hello ";
    }
}

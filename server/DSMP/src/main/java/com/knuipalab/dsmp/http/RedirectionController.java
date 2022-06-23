package com.knuipalab.dsmp.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class RedirectionController {

    @Value("${hostLocation}")
    private String hostLocation;

    @GetMapping("/redirectToMainPage")
    public RedirectView redirectToMainPage(){
        return new RedirectView("http://"+hostLocation+":3001/");
    }
}

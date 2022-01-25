package com.knuipalab.dsmp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectionController {
    @GetMapping("/redirectToMainPage")
    public RedirectView redirectToMainPage(){
        return new RedirectView("http://imdc.hopto.org:3001/");
    }
}

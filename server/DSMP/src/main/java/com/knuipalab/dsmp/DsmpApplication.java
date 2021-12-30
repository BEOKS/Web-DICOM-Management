package com.knuipalab.dsmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class DsmpApplication {
    @RequestMapping("/")
    public String home() {
        return "index";
    }
    public static void main(String[] args) {
        SpringApplication.run(DsmpApplication.class, args);
    }

}

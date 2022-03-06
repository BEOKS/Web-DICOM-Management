package com.knuipalab.dsmp;

import com.knuipalab.dsmp.configuration.auth.dto.SessionUser;
import com.knuipalab.dsmp.util.log.ExeTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@SpringBootApplication(exclude={MultipartAutoConfiguration.class})
@Controller
@RequiredArgsConstructor
public class DsmpApplication {

    private final HttpSession httpSession;

    @RequestMapping("/")
    public String home(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user !=  null) {
            model.addAttribute("userName",user.getName());
        }
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(DsmpApplication.class, args);
    }

}

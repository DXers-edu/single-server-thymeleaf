package com.dxers.single_server_thymeleaf.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    
    @GetMapping(value={"/", "/home"})
    public String home(
        @SessionAttribute(name = "userEmail", required = false) String userEmail
    ) {
        if (userEmail == null) return "redirect:/auth/sign-in";
        return "home";
    }

}

package com.project.ems.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String getHomePage() {
        return "redirect:/employees";
    }
}

package com.example.MainProject.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoController {
    @GetMapping("hit")
    public String sayHello () {
        try {
            return "Working";
        } catch (Exception e) {
            return "Not Authorized";
        }
    }
}

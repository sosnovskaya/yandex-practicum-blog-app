package edu.misosnovskaya.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String homePage() {
        return "<h1>Hello, world!</h1>";
    }
}

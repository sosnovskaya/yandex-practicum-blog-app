package edu.misosnovskaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class DefaultController {
    @GetMapping("/")
    public String get() {
        return "redirect:/posts";
    }
}

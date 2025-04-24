package edu.misosnovskaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class PostController {
    @GetMapping("/")
    public String get() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getPosts() {
        return "posts";
    }
}

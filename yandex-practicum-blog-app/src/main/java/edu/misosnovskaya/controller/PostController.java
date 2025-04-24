package edu.misosnovskaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping()
public class PostController {

    @GetMapping("/posts")
    public String getPosts() {
        return "posts";
    }

    @GetMapping("/posts")
    public String getPosts(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            Model model) {
        return "posts";
    }

    @GetMapping("/posts{id}")
    public String getPost(@PathVariable("id") Long id, Model model) {

        return "post";
    }

    @GetMapping("/posts/add")
    public String addPost() {
        return "add-post";
    }

    @PostMapping("/posts")
    public String addPost(
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestParam("tags") String tags,
            @RequestParam("image") MultipartFile image) {
        return "redirect:/posts/" + "postId";
    }

    @PostMapping("/posts/{id}/like")
    public String likePost(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "like") boolean like) {
        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable(name = "id") Long id, Model model) {
        return "add-post";
    }

    @PostMapping("/posts/{id}")
    public String editPost(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam(name = "tags", required = false, defaultValue = "") String tags) {
        return "redirect:/posts/" + id;
    }
}

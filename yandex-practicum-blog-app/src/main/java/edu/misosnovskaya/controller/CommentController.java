package edu.misosnovskaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
public class CommentController {

    @PostMapping("/posts/{id}/comments")
    public String addComment(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "text") String text) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments/{commentId}")
    public String updateComment(
            @PathVariable(name = "id") Long id,
            @PathVariable(name = "commentId") Long commentId,
            @RequestParam(name = "text") String text) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable(name = "id") Long id,
            @PathVariable(name = "commentId") Long commentId) {
        return "redirect:/posts/" + id;
    }
}

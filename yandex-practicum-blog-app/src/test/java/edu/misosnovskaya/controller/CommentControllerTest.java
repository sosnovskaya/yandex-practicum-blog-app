package edu.misosnovskaya.controller;

import edu.misosnovskaya.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @MockBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddComment() throws Exception {
        mockMvc.perform(post("/posts/1/comments")
                        .param("id", "id")
                        .param("commentId", "commentId")
                        .param("text", "text"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/posts/*"));
    }

    @Test
    void testUpdateComment() throws Exception {
        mockMvc.perform(post("/posts/1/comments/1")
                        .param("id", "id")
                        .param("commentId", "commentId")
                        .param("text", "text"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/posts/*"));
    }

    @Test
    void testDeleteComment() throws Exception {
        mockMvc.perform(post("/posts/1/comments/1/delete")
                        .param("id", "id")
                        .param("commentId", "commentId"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/posts/*"));
    }
}
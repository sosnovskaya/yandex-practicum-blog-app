package edu.misosnovskaya.controller;

import edu.misosnovskaya.config.TestConfig;
import edu.misosnovskaya.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = {TestConfig.class})
@WebAppConfiguration
class CommentControllerTest {

    @Autowired
    private CommentController commentController;
    @MockitoBean
    private CommentService commentService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

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
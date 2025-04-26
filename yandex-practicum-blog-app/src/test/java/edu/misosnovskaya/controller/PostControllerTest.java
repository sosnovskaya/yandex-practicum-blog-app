package edu.misosnovskaya.controller;

import edu.misosnovskaya.config.TestConfig;
import edu.misosnovskaya.service.PostService;
import edu.misosnovskaya.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitConfig(classes = {TestConfig.class})
@WebAppConfiguration
class PostControllerTest {

    @Autowired
    private PostController postController;

    @MockitoBean
    private PostService postService;

    private MockMvc mockMvc;

    MockMultipartFile imageFile;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        imageFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );
    }


    @Test
    void testAddPostRedirect() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"));
    }

    @Test
    void testAddPost() throws Exception {
        when(postService.addPost(any(), any(), any(), any())).thenReturn(TestUtils.getTestPost());
        mockMvc.perform(multipart("/posts")
                        .file(imageFile)
                        .param("title", "post title")
                        .param("text", "post text")
                        .param("tags", "#post #tag"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/posts/*"));
    }

    @Test
    void testDeletePost() throws Exception {
        mockMvc.perform(post("/posts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void testEditPostRedirect() throws Exception {
        mockMvc.perform(post("/posts/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-post"));
    }

    @Test
    void testEditPost() throws Exception {
        mockMvc.perform(multipart("/posts/1")
                        .file(imageFile)
                        .param("title", "post title")
                        .param("text", "post text")
                        .param("tags", "#post #tag"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/posts/*"));
    }
}
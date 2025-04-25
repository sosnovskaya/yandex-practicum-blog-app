package edu.misosnovskaya.controller;

import edu.misosnovskaya.config.TestConfig;
import edu.misosnovskaya.utils.SqlRequestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class CommentControllerTest {

    @Autowired
    private CommentController commentController;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        jdbcTemplate.execute(SqlRequestsUtils.DELETE_ALL_COMMENTS_SQL);
        jdbcTemplate.execute(SqlRequestsUtils.DELETE_ALL_POSTS_SQL);
        jdbcTemplate.execute("insert into posts(id, title, text, image_path, likes_count) values (1, 'post1', 'post text', 'path to image', 0)");
        jdbcTemplate.execute("insert into comments(id, post_id, text) values (1, 1, 'comment text')");
    }

    @Test
    void addComment() throws Exception {
        mockMvc.perform(post("/post")
                        .param("title", "titletitletitle")
                        .param("content", "contentcontentcontentcontentcontentcontent")
                        .param("tags", "tag1, tag2")
                        .param("imageUrl", "imageUrl"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post"));
    }

    @Test
    void updateComment() throws Exception {
        mockMvc.perform(post("/post")
                        .param("title", "titletitletitle")
                        .param("content", "contentcontentcontentcontentcontentcontent")
                        .param("tags", "tag1, tag2")
                        .param("imageUrl", "imageUrl"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post"));

    }

    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(post("/post")
                        .param("title", "titletitletitle")
                        .param("content", "contentcontentcontentcontentcontentcontent")
                        .param("tags", "tag1, tag2")
                        .param("imageUrl", "imageUrl"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post"));

    }
}
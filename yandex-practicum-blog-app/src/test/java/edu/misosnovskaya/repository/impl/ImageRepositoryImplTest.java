package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.config.TestConfig;
import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.repository.ImageRepository;
import edu.misosnovskaya.repository.PostRepository;
import edu.misosnovskaya.utils.SqlRequestUtils;
import edu.misosnovskaya.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@WebAppConfiguration
@SpringJUnitConfig(classes = {TestConfig.class})
class ImageRepositoryImplTest {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(SqlRequestUtils.DELETE_ALL_POSTS_SQL);
    }

    @Test
    void findImagePathByIdNoInfo() {
        Optional<String> imagePath = imageRepository.findImagePathById(1L);

        assertTrue(imagePath.isEmpty());
    }

    @Test
    void findImagePathById() {
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);

        Optional<String> imagePath = imageRepository.findImagePathById(savedPost.getId());

        assertEquals(savedPost.getImagePath(), imagePath.get());
    }
}
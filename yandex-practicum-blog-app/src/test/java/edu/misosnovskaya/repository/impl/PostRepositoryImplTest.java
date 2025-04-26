package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.config.TestConfig;
import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.entity.TagEntity;
import edu.misosnovskaya.repository.ImageRepository;
import edu.misosnovskaya.repository.PostRepository;
import edu.misosnovskaya.repository.TagRepository;
import edu.misosnovskaya.utils.SqlRequestUtils;
import edu.misosnovskaya.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebAppConfiguration
@SpringJUnitConfig(classes = {TestConfig.class})
class PostRepositoryImplTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(SqlRequestUtils.DELETE_ALL_POST_TO_TAGS_SQL);
        jdbcTemplate.execute(SqlRequestUtils.DELETE_ALL_TAGS_SQL);
        jdbcTemplate.execute(SqlRequestUtils.DELETE_ALL_POSTS_SQL);
    }

    @Test
    void findPosts() {
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost1 = postRepository.insertPost(post);
        PostEntity savedPost2 = postRepository.insertPost(post);
        TagEntity tag = new TagEntity("tag");
        Long tagId = tagRepository.getOrCreateTagId(tag.getName());
        tagRepository.insertPostTags(savedPost1.getId(), tagId);
        tagRepository.insertPostTags(savedPost2.getId(), tagId);

        List<PostEntity> posts = postRepository.findPosts(tag.getName(), 10, 0);

        assertFalse(posts.isEmpty());
        assertEquals(2, posts.size());
    }

    @Test
    void testFindPost() {
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);

        Optional<PostEntity> dbPost = postRepository.findPost(savedPost.getId());

        assertAll(
                () -> assertNotNull(dbPost.get()),
                () -> assertNotNull(dbPost.get().getId())
        );
    }

    @Test
    void testInsertPost() {
        PostEntity post = TestUtils.getTestPostEntity();

        PostEntity savedPost = postRepository.insertPost(post);

        Optional<PostEntity> dbPost = postRepository.findPost(savedPost.getId());
        PostEntity actualPost = dbPost.get();
        String imagePath = imageRepository.findImagePathById(savedPost.getId()).get();
        assertAll(
                () -> assertNotNull(savedPost.getId()),
                () -> assertNotNull(actualPost),
                () -> assertEquals(savedPost, actualPost),
                () -> assertEquals(imagePath, actualPost.getImagePath())
        );
    }

    @Test
    void updatePost() {
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);
        String editedText = "editedText";
        savedPost.setText(editedText);

        postRepository.updatePost(savedPost);

        PostEntity updatedPost = postRepository.findPost(savedPost.getId()).get();
        assertEquals(editedText, updatedPost.getText());
    }

    @Test
    void testDeletePost() {
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);

        postRepository.deletePost(savedPost.getId());

        Optional<PostEntity> actualPost = postRepository.findPost(savedPost.getId());
        assertTrue(actualPost.isEmpty());
    }

    @Test
    void updateLikesCount() {
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);

        postRepository.updateLikesCount(savedPost.getId(), true);
        postRepository.updateLikesCount(savedPost.getId(), true);
        postRepository.updateLikesCount(savedPost.getId(), true);
        postRepository.updateLikesCount(savedPost.getId(), false);

        Optional<PostEntity> updatedPost = postRepository.findPost(savedPost.getId());
        assertTrue(updatedPost.isPresent());
        assertEquals(2, updatedPost.get().getLikesCount());
    }
}
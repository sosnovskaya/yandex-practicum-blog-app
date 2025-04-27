package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.entity.CommentEntity;
import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.repository.CommentRepository;
import edu.misosnovskaya.repository.PostRepository;
import edu.misosnovskaya.utils.SqlRequestUtils;
import edu.misosnovskaya.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CommentRepositoryImplTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(SqlRequestUtils.DELETE_ALL_COMMENTS_SQL);
        jdbcTemplate.execute(SqlRequestUtils.DELETE_ALL_POSTS_SQL);
    }

    @Test
    void testInsertComment() {
        String comment = "comment";
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);

        commentRepository.insertComment(savedPost.getId(), comment);

        List<CommentEntity> comments = jdbcTemplate.query(
                SqlRequestUtils.SELECT_POST_COMMENTS_SQL, SqlRequestUtils.commentRowMapper, savedPost.getId());
        assertEquals(1, comments.size());
        assertEquals(comment, comments.getFirst().getText());
    }

    @Test
    void testUpdateComment() {
        String comment = "comment";
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);

        commentRepository.insertComment(savedPost.getId(), comment);

        List<CommentEntity> comments = jdbcTemplate.query(
                SqlRequestUtils.SELECT_POST_COMMENTS_SQL, SqlRequestUtils.commentRowMapper, savedPost.getId());
        assertEquals(1, comments.size());
        assertEquals(comment, comments.getFirst().getText());
    }

    @Test
    void testDeleteComment() {
        String comment = "comment";
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);
        Long commentId = commentRepository.insertComment(savedPost.getId(), comment);

        commentRepository.deleteComment(savedPost.getId(), commentId);

        List<CommentEntity> comments = jdbcTemplate.query(
                SqlRequestUtils.SELECT_POST_COMMENTS_SQL, SqlRequestUtils.commentRowMapper, savedPost.getId());
        assertTrue(comments.isEmpty());
    }

    @Test
    void testGetComments() {
        String comment1 = "comment1";
        String comment2 = "comment2";
        PostEntity post = TestUtils.getTestPostEntity();
        PostEntity savedPost = postRepository.insertPost(post);
        Long comment1Id = commentRepository.insertComment(savedPost.getId(), comment1);
        Long comment2Id = commentRepository.insertComment(savedPost.getId(), comment2);

        List<CommentEntity> comments = commentRepository.getComments(savedPost.getId());

        assertFalse(comments.isEmpty());
    }
}
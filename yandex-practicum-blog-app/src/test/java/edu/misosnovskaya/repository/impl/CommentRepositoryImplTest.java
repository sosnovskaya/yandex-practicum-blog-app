package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.config.TestConfig;
import edu.misosnovskaya.repository.CommentRepository;
import edu.misosnovskaya.utils.SqlRequestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class CommentRepositoryImplTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute(SqlRequestsUtils.DELETE_ALL_COMMENTS_SQL);
        jdbcTemplate.execute(SqlRequestsUtils.DELETE_ALL_POSTS_SQL);
    }

    @Test
    void testAddComment() {

    }

    @Test
    void testEditComment() {
    }

    @Test
    void testDeleteComment() {
    }
}
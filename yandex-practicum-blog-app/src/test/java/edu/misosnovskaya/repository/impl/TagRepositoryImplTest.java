package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.config.TestConfig;
import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.entity.TagEntity;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebAppConfiguration
@SpringJUnitConfig(classes = {TestConfig.class})
class TagRepositoryImplTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void dearDown() {
        jdbcTemplate.execute(SqlRequestUtils.DELETE_ALL_POST_TO_TAGS_SQL);
        jdbcTemplate.execute(SqlRequestUtils.DELETE_ALL_TAGS_SQL);
    }

    @Test
    void testInsertTags() {
        TagEntity tag1 = new TagEntity("#tag1");
        TagEntity tag2 = new TagEntity("#tag2");
        List<TagEntity> tags = List.of(tag1, tag2);
        PostEntity post = TestUtils.getTestPostEntity();
        Long postId = postRepository.insertPost(post).getId();

        tagRepository.insertPostTags(postId, tags);

        List<TagEntity> actualTags = jdbcTemplate.query(SqlRequestUtils.SELECT_TAGS_SQL, SqlRequestUtils.tagRowMapper);
        assertThat(actualTags).hasSize(2);
        assertThat(actualTags)
                .extracting(TagEntity::getName)
                .containsExactlyInAnyOrder("#tag1", "#tag2");
    }

    @Test
    void testGetPostTags() {
        TagEntity tag1 = new TagEntity("#tag1");
        TagEntity tag2 = new TagEntity("#tag2");
        List<TagEntity> tags = List.of(tag1, tag2);
        PostEntity post = TestUtils.getTestPostEntity();
        Long postId = postRepository.insertPost(post).getId();
        tagRepository.insertPostTags(postId, tags);

        List<TagEntity> dbTags = tagRepository.getPostTags(postId);

        assertAll(
                () -> assertThat(dbTags)
                        .hasSameSizeAs(dbTags)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(tags),
                () -> assertThat(dbTags)
                        .extracting(TagEntity::getName)
                        .containsExactlyInAnyOrder("#tag1", "#tag2")
        );
    }

    @Test
    void testDeletePostTags() {
        TagEntity tag1 = new TagEntity("#tag1");
        TagEntity tag2 = new TagEntity("#tag2");
        List<TagEntity> tags = List.of(tag1, tag2);
        PostEntity post = TestUtils.getTestPostEntity();
        Long postId = postRepository.insertPost(post).getId();
        tagRepository.insertPostTags(postId, tags);

        tagRepository.deletePostTags(postId);

        List<TagEntity> dbTags = tagRepository.getPostTags(postId);
        assertTrue(dbTags.isEmpty());
    }
}
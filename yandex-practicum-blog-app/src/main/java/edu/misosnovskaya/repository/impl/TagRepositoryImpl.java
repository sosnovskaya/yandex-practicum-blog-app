package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.entity.TagEntity;
import edu.misosnovskaya.repository.TagRepository;
import edu.misosnovskaya.utils.SqlRequestUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert tagInsert;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tags")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public void insertPostTags(Long postId, List<TagEntity> tags) {
        List<Long> tagIds = tags.stream()
                .map(tag -> getOrCreateTagId(tag.getName()))
                .toList();
        tagIds.forEach(tagId -> insertPostTags(postId, tagId));
    }

    @Override
    public List<TagEntity> getPostTags(Long postId) {
        return jdbcTemplate.query(SqlRequestUtils.SELECT_POST_TAGS_LINK_SQL, SqlRequestUtils.tagRowMapper, postId);
    }

    @Override
    public void deletePostTags(Long postId) {
        jdbcTemplate.update(SqlRequestUtils.DELETE_POST_TO_TAGS_SQL, postId);
    }

    @Override
    public void insertPostTags(Long postId, Long tagId) {
        jdbcTemplate.update(SqlRequestUtils.INSERT_POST_TO_TAG_SQL, postId, tagId);
    }

    @Override
    public Long getOrCreateTagId(String tagName) {
        Optional<Long> existingId = findTagIdByName(tagName);
        return existingId.orElseGet(() -> tagInsert.executeAndReturnKey(
                Map.of("name", tagName)
        ).longValue());
    }

    private Optional<Long> findTagIdByName(String tagName) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(SqlRequestUtils.SELECT_TAG_BY_NAME_SQL, Long.class, tagName));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

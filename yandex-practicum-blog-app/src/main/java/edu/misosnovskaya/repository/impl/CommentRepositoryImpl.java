package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.exceptions.CommonDBException;
import edu.misosnovskaya.repository.CommentRepository;
import edu.misosnovskaya.utils.SqlRequestUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert commentInsert;

    CommentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("comments")
                .usingGeneratedKeyColumns("id")
                .usingColumns("post_id", "text");
    }

    @Override
    public Long insertComment(Long postId, String text) {
        return commentInsert.executeAndReturnKey(
                Map.of(
                        "post_id", postId,
                        "text", text
                )).longValue();
    }

    @Override
    public void updateComment(Long postId, Long commentId, String text) {
        try {
            jdbcTemplate.update(SqlRequestUtils.UPDATE_COMMENT_SQL, text, commentId, postId);
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during updating post comment, [postId = %s, text = %s]", postId, text),
                    e
            );
        }
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        try {
            jdbcTemplate.update(SqlRequestUtils.DELETE_COMMENT_SQL, commentId);
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during deleting post comment, [postId = %s]", postId),
                    e
            );
        }
    }
}

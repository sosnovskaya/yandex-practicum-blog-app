package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.exceptions.CommonDBException;
import edu.misosnovskaya.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insertComment(Long postId, String text) {
        try {
            jdbcTemplate.update(INSERT_COMMENT_SQL, postId, text);
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during inserting post comment, [postId = %s, text = %s]", postId, text),
                    e
            );
        }
    }

    @Override
    public void updateComment(Long postId, Long commentId, String text) {
        try {
            jdbcTemplate.update(UPDATE_COMMENT_SQL, text, commentId, postId);
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
            jdbcTemplate.update(DELETE_COMMENT_SQL, commentId);
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during deleting post comment, [postId = %s]", postId),
                    e
            );
        }
    }

    private static final String INSERT_COMMENT_SQL = """
                insert into comments (post_id, text) values (?, ?)
            """;

    private static final String UPDATE_COMMENT_SQL = """
                update comments set text = ? where id = ? and post_id = ?
            """;

    private static final String DELETE_COMMENT_SQL = """
                delete from comments where id = ?
            """;
}

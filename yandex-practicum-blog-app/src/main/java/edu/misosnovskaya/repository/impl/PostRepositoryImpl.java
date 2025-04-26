package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.exceptions.CommonDBException;
import edu.misosnovskaya.repository.PostRepository;
import edu.misosnovskaya.utils.SqlRequestUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert postInsert;

    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        postInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("posts")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "text", "image_path", "likes_count");
    }

    @Override
    public List<PostEntity> findPosts(String search, int pageSize, int pageNumber) {
        try {
            return namedParameterJdbcTemplate.query(
                    SqlRequestUtils.SEARCH_POSTS_SQL,
                    Map.of(
                            "tagName", search,
                            "limit", pageSize,
                            "offset", pageNumber * pageSize
                    ),
                    SqlRequestUtils.postRowMapper
            );
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during searching posts, [search=%s, pageSize=%d, pageNumber=%d]",
                            search, pageSize, pageNumber),
                    e
            );
        }
    }

    @Override
    public Optional<PostEntity> findPost(Long id) {
        try {
            PostEntity post = jdbcTemplate.queryForObject(
                    SqlRequestUtils.SELECT_POST_BY_ID_SQL,
                    SqlRequestUtils.postRowMapper,
                    id
            );
            return Optional.of(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during finding post, [id=%d]", id),
                    e
            );
        }
    }

    @Override
    public PostEntity insertPost(PostEntity post) {
        try {
            Long newId = postInsert.executeAndReturnKey(
                    Map.of(
                            "title", post.getTitle(),
                            "text", post.getText(),
                            "image_path", post.getImagePath(),
                            "likes_count", post.getLikesCount()
                    )).longValue();

            return PostEntity.builder()
                    .id(newId)
                    .title(post.getTitle())
                    .text(post.getText())
                    .imagePath(post.getImagePath())
                    .likesCount(post.getLikesCount())
                    .build();
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during saving post [title=%s]", post.getTitle()),
                    e
            );
        }
    }

    public void updatePost(PostEntity post) {
        try {
            namedParameterJdbcTemplate.update(
                    SqlRequestUtils.UPDATE_POST_SQL,
                    Map.of(
                            "title", post.getTitle(),
                            "text", post.getText(),
                            "imagePath", post.getImagePath(),
                            "id", post.getId()
                    )
            );
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during updating post [id=%d]", post.getId()),
                    e
            );
        }
    }

    @Override
    public void deletePost(Long id) {
        try {
            jdbcTemplate.update(SqlRequestUtils.DELETE_POST_SQL, id);
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during deleting post, [id = %d]", id),
                    e
            );
        }
    }


    @Override
    public void updateLikesCount(Long id, boolean like) {
        try {
            int increment = like ? 1 : -1;
            jdbcTemplate.update(
                    SqlRequestUtils.UPDATE_LIKES_SQL,
                    increment,
                    id
            );
        } catch (RuntimeException e) {
            throw new CommonDBException(
                    String.format("Exception during updating likes, [id=%d]", id),
                    e
            );
        }
    }
}

package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<String> findImagePathById(Long postId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_IMAGE_PATH_SQL, String.class, postId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final String SELECT_IMAGE_PATH_SQL = """
                select p.image_path
                 from posts p
                  where p.id = ?
            """;
}

package edu.misosnovskaya.repository;

import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository {
    Optional<String> findImagePathById(@Param("id") Long postId);
}

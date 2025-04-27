package edu.misosnovskaya.repository;

import java.util.Optional;

public interface ImageRepository {
    Optional<String> findImagePathById(Long postId);
}

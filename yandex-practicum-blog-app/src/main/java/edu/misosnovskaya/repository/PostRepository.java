package edu.misosnovskaya.repository;

import edu.misosnovskaya.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<PostEntity> findPosts(String search, int pageSize, int pageNumber);

    Optional<PostEntity> findPost(Long id);

    PostEntity insertPost(PostEntity post);

    void updatePost(PostEntity post);

    void deletePost(Long id);

    void updateLikesCount(Long id, boolean like);
}

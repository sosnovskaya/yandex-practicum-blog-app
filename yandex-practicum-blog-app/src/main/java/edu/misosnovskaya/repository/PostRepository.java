package edu.misosnovskaya.repository;

import edu.misosnovskaya.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<PostEntity> findPosts(String search, int pageSize, int pageNumber);

    Optional<PostEntity> findPost(Long id);

    PostEntity savePost(PostEntity post);

    PostEntity updatePost(PostEntity post);

    void deletePost(Long id);

    void updateLikesCount(Long id, boolean like);
}

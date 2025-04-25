package edu.misosnovskaya.repository.impl;

import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    @Override
    public List<PostEntity> findPosts(String search, int pageSize, int pageNumber) {
        return new ArrayList<>();
    }

    @Override
    public Optional<PostEntity> findPost(Long id) {
        return Optional.empty();
    }

    @Override
    public PostEntity savePost(PostEntity post) {
        return null;
    }

    @Override
    public PostEntity updatePost(PostEntity post) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }

    @Override
    public void updateLikesCount(Long id, boolean like) {

    }
}

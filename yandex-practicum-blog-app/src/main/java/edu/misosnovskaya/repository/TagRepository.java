package edu.misosnovskaya.repository;

import edu.misosnovskaya.entity.TagEntity;

import java.util.List;

public interface TagRepository {

    void insertPostTags(Long postId, List<TagEntity> tags);

    List<TagEntity> getPostTags(Long postId);

    void deletePostTags(Long postId);

    Long getOrCreateTagId(String tagName);

    void insertPostTags(Long postId, Long tagId);
}

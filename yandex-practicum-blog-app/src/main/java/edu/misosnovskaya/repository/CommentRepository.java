package edu.misosnovskaya.repository;

import edu.misosnovskaya.entity.CommentEntity;

import java.util.List;

public interface CommentRepository {
    Long insertComment(Long postId, String text);

    void updateComment(Long postId, Long commentId, String text);

    void deleteComment(Long postId, Long commentId);

    List<CommentEntity> getComments(Long postId);
}

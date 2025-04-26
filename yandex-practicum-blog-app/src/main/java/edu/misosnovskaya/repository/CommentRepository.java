package edu.misosnovskaya.repository;

public interface CommentRepository {
    Long insertComment(Long postId, String text);

    void updateComment(Long postId, Long commentId, String text);

    void deleteComment(Long postId, Long commentId);
}

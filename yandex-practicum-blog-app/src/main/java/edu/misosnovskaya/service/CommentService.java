package edu.misosnovskaya.service;

public interface CommentService {
    void addComment(Long postId, String text);

    void editComment(Long postId, Long commentId, String text);

    void deleteComment(Long postId, Long commentId);
}

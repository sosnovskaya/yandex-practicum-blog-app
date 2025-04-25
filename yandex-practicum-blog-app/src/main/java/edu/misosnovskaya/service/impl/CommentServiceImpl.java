package edu.misosnovskaya.service.impl;

import edu.misosnovskaya.repository.CommentRepository;
import edu.misosnovskaya.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void addComment(Long postId, String text) {
        commentRepository.insertComment(postId, text);
    }

    @Override
    public void editComment(Long postId, Long commentId, String text) {
        commentRepository.updateComment(postId, commentId, text);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        commentRepository.deleteComment(postId, commentId);
    }
}

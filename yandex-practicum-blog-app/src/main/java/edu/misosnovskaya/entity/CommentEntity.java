package edu.misosnovskaya.entity;

import lombok.Data;

@Data
public class CommentEntity {

    private Long id;

    private Long postId;

    private String text;
}

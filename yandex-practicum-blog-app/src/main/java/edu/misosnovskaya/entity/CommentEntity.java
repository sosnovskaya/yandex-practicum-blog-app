package edu.misosnovskaya.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentEntity {

    private Long id;

    private Long postId;

    private String text;
}

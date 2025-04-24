package edu.misosnovskaya.model;

import lombok.Data;

@Data
public class Comment {

    private Long id;

    private Long postId;

    private String text;
}

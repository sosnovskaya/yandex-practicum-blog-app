package edu.misosnovskaya.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    private Long id;
    private String title;
    private String text;
    private String imagePath;

    @Builder.Default
    private int likesCount = 0;

    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    private Set<TagEntity> tags = new HashSet<>();
}

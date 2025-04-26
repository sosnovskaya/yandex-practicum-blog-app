package edu.misosnovskaya.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
}

package edu.misosnovskaya.utils;

import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.model.Post;

public class TestUtils {
    public static PostEntity getTestPostEntity() {
        return PostEntity.builder()
                .title("title")
                .text("text")
                .imagePath("imagePath")
                .likesCount(0)
                .build();
    }

    public static Post getTestPost() {
        return Post.builder()
                .id(1L)
                .title("title")
                .text("text")
                .imagePath("imagePath")
                .likesCount(0)
                .build();
    }
}

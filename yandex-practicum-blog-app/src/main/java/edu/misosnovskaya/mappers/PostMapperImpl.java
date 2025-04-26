package edu.misosnovskaya.mappers;

import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapperImpl implements PostMapper {
    public Post toModel(PostEntity post) {
        return Post.builder()
                .id(post.getId())
                .title(post.getText())
                .text(post.getText())
                .imagePath(post.getImagePath())
                .likesCount(post.getLikesCount())
                .build();
    }
}

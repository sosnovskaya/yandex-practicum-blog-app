package edu.misosnovskaya.mappers;

import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.model.Post;

public interface PostMapper {
    Post toModel(PostEntity post);

}

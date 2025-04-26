package edu.misosnovskaya.mappers;

import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    Post toModel(PostEntity post);

    PostEntity toEntity(Post post);
}

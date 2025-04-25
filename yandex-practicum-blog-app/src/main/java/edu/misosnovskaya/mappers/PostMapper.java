package edu.misosnovskaya.mappers;

import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.entity.TagEntity;
import edu.misosnovskaya.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    @Mapping(target = "tags", source = "tags")
    Post toModel(PostEntity post);

    @Mapping(target = "tags", source = "tags")
    PostEntity toEntity(Post post);

    default Set<TagEntity> mapTags(Set<String> tags) {
        if (tags == null) {
            return new HashSet<>();
        }
        return tags.stream()
                .map(tag -> TagEntity.builder().name(tag).build())
                .collect(Collectors.toSet());
    }

    default Set<String> mapTagEntities(Set<TagEntity> tags) {
        if (tags == null) {
            return new HashSet<>();
        }
        return tags.stream()
                .map(TagEntity::getName)
                .collect(Collectors.toSet());
    }
}

package edu.misosnovskaya.model;

import edu.misosnovskaya.entity.CommentEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Post {

    private Long id;

    private String title;

    private String text;

    private String imagePath;

    private int likesCount;

    private List<CommentEntity> comments = new ArrayList<>();

    private Set<String> tags = new HashSet<>();

    public String getTextParts() {
        return text.length() > 300 ? text.substring(0, 300) + "..." : text;
    }

    public String getTagsAsText() {
        return String.join(" ", tags);
    }

}

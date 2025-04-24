package edu.misosnovskaya.model;

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

    private List<Comment> comments = new ArrayList<>();

    private Set<Tag> tags = new HashSet<>();

}

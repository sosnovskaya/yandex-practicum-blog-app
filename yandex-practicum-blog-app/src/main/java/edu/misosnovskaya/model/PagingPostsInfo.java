package edu.misosnovskaya.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagingPostsInfo {
    private Paging paging;
    private List<Post> posts;
}

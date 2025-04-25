package edu.misosnovskaya.service;

import edu.misosnovskaya.model.PagingPostsInfo;
import edu.misosnovskaya.model.Post;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    Post addPost(String title, String text, MultipartFile image, String tags);

    Post getPost(Long postId);

    Post editPost(Long id, String title, String text, MultipartFile image, String tags);

    PagingPostsInfo findPagingPosts(String search, int pageSize, int pageNumber);

    void updateLikesCount(Long id, boolean like);
}

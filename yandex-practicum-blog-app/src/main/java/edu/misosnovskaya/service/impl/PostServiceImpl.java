package edu.misosnovskaya.service.impl;

import edu.misosnovskaya.configuration.AppValues;
import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.exceptions.PostNotFoundException;
import edu.misosnovskaya.mappers.PostMapper;
import edu.misosnovskaya.model.Paging;
import edu.misosnovskaya.model.PagingPostsInfo;
import edu.misosnovskaya.model.Post;
import edu.misosnovskaya.repository.PostRepository;
import edu.misosnovskaya.service.PostService;
import edu.misosnovskaya.utils.PostProcessUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final AppValues appValues;

    private final PostMapper postMapper;

    private final PostRepository postRepository;

    private final PostProcessUtils postProcessUtils;

    @Override
    public Post addPost(String title, String text, MultipartFile image, String tags) {
        log.info(String.format("Start adding post, [title = %s, text = %s, tags = %s].", title, text, tags));

        PostEntity newPost = PostEntity.builder()
                .title(title)
                .text(text)
                .imagePath(postProcessUtils.storeFileToPath(image, appValues.getUploadDir()))
                .tags(postProcessUtils.extractHashtags(tags))
                .build();
        PostEntity savedPost = postRepository.savePost(newPost);

        log.info(String.format("Post added successfully, [post = %s].", savedPost));

        return postMapper.toModel(savedPost);
    }

    @Override
    public Post getPost(Long postId) {
        log.info(String.format("Start searching post, [postId = %s].", postId));

        PostEntity post = postRepository.findPost(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("Can not find post, [postId = %s]", postId)));

        log.info(String.format("Post is found, [post = %s].", post));

        return postMapper.toModel(post);
    }

    @Override
    public Post editPost(Long id, String title, String text, MultipartFile image, String tags) {
        return null;
    }

    @Override
    public PagingPostsInfo findPagingPosts(String search, int pageSize, int pageNumber) {
        log.info(String.format("Start searching posts, [search = %s, pageSize = %s, pageNumber = %s].",
                search, pageSize, pageNumber));

        List<PostEntity> posts = postRepository.findPosts(search, pageSize, pageNumber);
        log.info(String.format("Posts are found, [posts = %s].", posts));

        return new PagingPostsInfo(
                Paging.getPaging(pageSize, pageNumber, posts.size()),
                posts.stream().map(postMapper::toModel).collect(Collectors.toList())
        );
    }

    @Override
    public void updateLikesCount(Long id, boolean like) {

    }
}

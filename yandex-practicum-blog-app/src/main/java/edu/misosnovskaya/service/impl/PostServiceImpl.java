package edu.misosnovskaya.service.impl;

import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.entity.TagEntity;
import edu.misosnovskaya.exceptions.PostNotFoundException;
import edu.misosnovskaya.mappers.PostMapper;
import edu.misosnovskaya.model.Paging;
import edu.misosnovskaya.model.PagingPostsInfo;
import edu.misosnovskaya.model.Post;
import edu.misosnovskaya.repository.CommentRepository;
import edu.misosnovskaya.repository.PostRepository;
import edu.misosnovskaya.repository.TagRepository;
import edu.misosnovskaya.service.PostService;
import edu.misosnovskaya.utils.PostProcessUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PostProcessUtils postProcessUtils;

    private final TagRepository tagRepository;

    @Override
    public Post addPost(String title, String text, MultipartFile image, String tags) {
        log.info(String.format("Start adding post, [title = %s, text = %s, tags = %s].", title, text, tags));

        Set<TagEntity> extractedTags = postProcessUtils.extractHashtags(tags);
        String imagePath = postProcessUtils.storeFileToPath(image);

        PostEntity newPost = PostEntity.builder()
                .title(title)
                .text(text)
                .imagePath(imagePath)
                .build();
        PostEntity savedPost = postRepository.insertPost(newPost);
        tagRepository.insertPostTags(savedPost.getId(), extractedTags.stream().toList());
        log.info(String.format("Post added successfully, [post = %s].", savedPost));

        return postMapper.toModel(savedPost);
    }

    @Override
    public Post getPost(Long postId) {
        log.info(String.format("Start searching post, [postId = %s].", postId));

        PostEntity postEntity = postRepository.findPost(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("Can not find post, [postId = %s]", postId)));

        log.info(String.format("Post is found, [post = %s].", postEntity));

        Post post = postMapper.toModel(postEntity);
        post.setComments(commentRepository.getComments(postId));
        return post;
    }

    @Override
    @Transactional
    public void editPost(Long id, String title, String text, MultipartFile image, String tags) {
        log.info(String.format("Start updating post, [title = %s, text = %s, tags = %s].", title, text, tags));

        Set<TagEntity> extractedTags = postProcessUtils.extractHashtags(tags);
        String newImagePath = postProcessUtils.storeFileToPath(image);

        PostEntity updatingPost = postRepository.findPost(id).orElseThrow(() -> new PostNotFoundException("Post is not found!"));
        postProcessUtils.deleteFile(updatingPost.getImagePath());
        tagRepository.deletePostTags(id);

        PostEntity newPost = PostEntity.builder()
                .id(id)
                .title(title)
                .text(text)
                .imagePath(newImagePath)
                .build();
        postRepository.updatePost(newPost);
        tagRepository.insertPostTags(id, extractedTags.stream().toList());

        log.info(String.format("Post updated successfully, [postId = %s].", id));
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
        postRepository.updateLikesCount(id, like);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deletePost(id);
    }
}

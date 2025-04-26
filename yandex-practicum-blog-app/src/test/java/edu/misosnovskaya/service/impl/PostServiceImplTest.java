package edu.misosnovskaya.service.impl;

import edu.misosnovskaya.entity.CommentEntity;
import edu.misosnovskaya.entity.PostEntity;
import edu.misosnovskaya.entity.TagEntity;
import edu.misosnovskaya.mappers.PostMapper;
import edu.misosnovskaya.model.Post;
import edu.misosnovskaya.repository.CommentRepository;
import edu.misosnovskaya.repository.PostRepository;
import edu.misosnovskaya.repository.TagRepository;
import edu.misosnovskaya.utils.PostProcessUtils;
import edu.misosnovskaya.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private PostProcessUtils postProcessUtils;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    private String title;
    private String text;
    private MultipartFile image;
    private String tagsInput;
    private Set<TagEntity> extractedTags;
    private String imagePath;
    private PostEntity savedPost;

    @BeforeEach
    void setUp() {
        title = "Test Title";
        text = "Test Content";
        image = new MockMultipartFile("test.jpg", new byte[]{0x01});
        tagsInput = "#test #example";
        extractedTags = Set.of(
                new TagEntity("#test"),
                new TagEntity("#example")
        );
        imagePath = "/uploads/test.jpg";
        savedPost = PostEntity.builder()
                .id(1L)
                .title(title)
                .text(text)
                .imagePath(imagePath)
                .build();
    }

    @Test
    void testAddPost() {
        Post expectedPost = new Post(1L, title, text, imagePath, 0, List.of(), Set.of("#test", "#example"));
        when(postProcessUtils.extractHashtags(tagsInput)).thenReturn(extractedTags);
        when(postProcessUtils.storeFileToPath(image)).thenReturn(imagePath);
        when(postRepository.insertPost(any(PostEntity.class))).thenReturn(savedPost);
        when(postMapper.toModel(savedPost)).thenReturn(expectedPost);

        Post result = postService.addPost(title, text, image, tagsInput);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(expectedPost, result)
        );
        verify(postProcessUtils).extractHashtags(tagsInput);
        verify(postProcessUtils).storeFileToPath(image);
        verify(postRepository).insertPost(any(PostEntity.class));
        verify(tagRepository).insertPostTags(1L, extractedTags.stream().toList());
        verify(postMapper).toModel(savedPost);
    }

    @Test
    void testEditPost() {
        String oldImagePath = "/uploads/old.jpg";
        String newImagePath = "/uploads/new.jpg";
        Set<TagEntity> newExtractedTags = Set.of(
                new TagEntity("#test"),
                new TagEntity("#example")
        );
        PostEntity updatingPost = new PostEntity(
                1L,
                "Old Title",
                "Old Text",
                oldImagePath,
                0
        );
        when(postProcessUtils.extractHashtags(tagsInput)).thenReturn(newExtractedTags);
        when(postProcessUtils.storeFileToPath(image)).thenReturn(newImagePath);
        when(postRepository.findPost(1L)).thenReturn(Optional.of(updatingPost));


        postService.editPost(1L, title, text, image, tagsInput);


        verify(postProcessUtils).deleteFile(oldImagePath);
        verify(postProcessUtils).storeFileToPath(image);

        ArgumentCaptor<PostEntity> postCaptor = ArgumentCaptor.forClass(PostEntity.class);
        verify(postRepository).updatePost(postCaptor.capture());
        PostEntity updatedPost = postCaptor.getValue();
        assertEquals(newImagePath, updatedPost.getImagePath());
        assertEquals(title, updatedPost.getTitle());
        assertEquals(text, updatedPost.getText());
        verify(tagRepository).deletePostTags(1L);
    }

    @Test
    void testGetPost() {
        CommentEntity comment = TestUtils.getCommentEntity();
        Long postId = 1L;
        PostEntity postEntity = TestUtils.getTestPostEntity();
        postEntity.setId(postId);
        Post post = TestUtils.getTestPost();
        when(postRepository.findPost(postId)).thenReturn(Optional.of(postEntity));
        when(postMapper.toModel(postEntity)).thenReturn(post);
        when(commentRepository.getComments(postId)).thenReturn(List.of(comment));

        Post result = postService.getPost(postId);

        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals(1, result.getComments().size());
        assertEquals(comment.getText(), result.getComments().get(0).getText());
        verify(postRepository).findPost(postId);
        verify(postMapper).toModel(postEntity);
        verify(commentRepository).getComments(postId);
    }
}
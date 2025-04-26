package edu.misosnovskaya.service.impl;

import edu.misosnovskaya.exceptions.ImageNotFoundException;
import edu.misosnovskaya.repository.ImageRepository;
import edu.misosnovskaya.service.ImageService;
import edu.misosnovskaya.utils.PostProcessUtils;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final PostProcessUtils postProcessUtils;

    private final ImageRepository imageRepository;

    @Override
    public ByteArrayResource getImage(Long postId) {
        String imagePath = imageRepository.findImagePathById(postId)
                .orElseThrow(() -> new ImageNotFoundException(String.format("No image for post, [postId = %s]", postId)));
        return postProcessUtils.getFileFromPath(imagePath);
    }
}

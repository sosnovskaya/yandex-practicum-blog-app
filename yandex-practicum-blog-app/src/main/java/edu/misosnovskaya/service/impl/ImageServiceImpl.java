package edu.misosnovskaya.service.impl;

import edu.misosnovskaya.exceptions.ImageNotFoundException;
import edu.misosnovskaya.repository.ImageRepository;
import edu.misosnovskaya.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Resource getImage(Long postId) {
        String imagePath = imageRepository.findImagePathById(postId)
                .orElseThrow(() -> new ImageNotFoundException(String.format("No image for post, [postId = %s]", postId)));
        return null;
    }
}

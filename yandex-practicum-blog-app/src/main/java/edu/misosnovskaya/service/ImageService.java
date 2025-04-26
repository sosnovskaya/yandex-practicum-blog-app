package edu.misosnovskaya.service;

import org.springframework.core.io.ByteArrayResource;

public interface ImageService {
    ByteArrayResource getImage(Long postId);
}

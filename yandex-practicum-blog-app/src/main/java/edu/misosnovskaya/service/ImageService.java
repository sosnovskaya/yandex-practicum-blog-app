package edu.misosnovskaya.service;

import org.springframework.core.io.Resource;

public interface ImageService {
    Resource getImage(Long postId);
}

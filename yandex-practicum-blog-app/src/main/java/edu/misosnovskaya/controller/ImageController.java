package edu.misosnovskaya.controller;

import edu.misosnovskaya.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/images/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable("id") Long id) {
        ByteArrayResource resource = imageService.getImage(id);

        return resource != null
                ? ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource)
                : ResponseEntity.notFound().build();
    }
}

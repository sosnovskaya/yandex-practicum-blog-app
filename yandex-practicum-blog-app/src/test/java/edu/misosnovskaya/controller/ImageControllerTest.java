package edu.misosnovskaya.controller;

import edu.misosnovskaya.service.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImageControllerTest.class)
class ImageControllerTest {

    @MockBean
    private ImageService imageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetImageSuccessfully() throws Exception {
        byte[] testImage = new byte[]{0x12, 0x34, 0x56, 0x78};
        ByteArrayResource resource = new ByteArrayResource(testImage);

        when(imageService.getImage(1L)).thenReturn(resource);

        mockMvc.perform(get("/images/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(testImage));
    }

    @Test
    void testGetImageNotFound() throws Exception {
        when(imageService.getImage(anyLong())).thenReturn(null);

        mockMvc.perform(get("/images/999"))
                .andExpect(status().isNotFound());
    }

    private static ResultMatcher bytes(byte[] expected) {
        return result -> {
            byte[] actual = result.getResponse().getContentAsByteArray();
            Assertions.assertArrayEquals(expected, actual, "Byte array mismatch");
        };
    }
}
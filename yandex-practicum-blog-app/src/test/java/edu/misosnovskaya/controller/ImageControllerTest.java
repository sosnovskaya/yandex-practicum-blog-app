package edu.misosnovskaya.controller;

import edu.misosnovskaya.config.TestConfig;
import edu.misosnovskaya.service.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = {TestConfig.class})
@WebAppConfiguration
class ImageControllerTest {
    @Autowired
    private ImageController imageController;

    @MockitoBean
    private ImageService imageService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

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
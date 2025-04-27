package edu.misosnovskaya.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppValues {

    @Value("${file.upload-dir}")
    private String uploadDir;
}

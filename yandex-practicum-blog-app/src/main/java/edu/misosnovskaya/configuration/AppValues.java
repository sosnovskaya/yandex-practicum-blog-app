package edu.misosnovskaya.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class AppValues {

    @Value("${file.upload-dir}")
    private String uploadDir;
}

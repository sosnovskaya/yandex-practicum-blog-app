package edu.misosnovskaya;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "edu.misosnovskaya")
@PropertySource("classpath:application.properties")
public class WebConfiguration {
}

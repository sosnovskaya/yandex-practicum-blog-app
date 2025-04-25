package edu.misosnovskaya.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "edu.misosnovskaya")
@PropertySource("classpath:application-test.properties")
public class TestConfig {

}

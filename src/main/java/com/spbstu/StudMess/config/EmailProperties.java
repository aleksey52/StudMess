package com.spbstu.StudMess.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "email")
@Getter
@Setter
public class EmailProperties {

    private String name;
    private String email;
    private String url;
    private String subject;
    private String content;
}

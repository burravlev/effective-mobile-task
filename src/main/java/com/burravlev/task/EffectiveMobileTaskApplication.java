package com.burravlev.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class EffectiveMobileTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(EffectiveMobileTaskApplication.class, args);
    }

}

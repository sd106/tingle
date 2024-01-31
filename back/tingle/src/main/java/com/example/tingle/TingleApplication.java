package com.example.tingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TingleApplication.class, args);
    }

}

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("prod")
public class ProdApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProdApplication.class, args);
    }
}

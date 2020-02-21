package com.example.facebookbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FacebookBackendApplication {
    public static void main(String[] args) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("my-secret"));
//		System.out.println(encoder.matches("my-secret", "$2a$10$1B3CLhTWseUyOLvvUTgtOuiiwbb8uEMTkWc44uETl.4YhzgViHczu"));
        SpringApplication.run(FacebookBackendApplication.class, args);
    }
}

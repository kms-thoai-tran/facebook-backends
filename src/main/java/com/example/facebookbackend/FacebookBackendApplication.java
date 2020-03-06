package com.example.facebookbackend;

import com.example.facebookbackend.service.IDynamoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FacebookBackendApplication implements CommandLineRunner {

    @Autowired
    IDynamoDbService dynamoDbService;


    public static void main(String[] args) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("my-secret"));
//		System.out.println(encoder.matches("my-secret", "$2a$10$1B3CLhTWseUyOLvvUTgtOuiiwbb8uEMTkWc44uETl.4YhzgViHczu"));
        SpringApplication.run(FacebookBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Mono<Map<String, AttributeValue>> test =  dynamoDbService.putItem(new HashMap<>());
//        Mono<Collection<AttributeValue>> test1 = test.map(x -> x.values());
//        System.out.println(test1);
    }
}

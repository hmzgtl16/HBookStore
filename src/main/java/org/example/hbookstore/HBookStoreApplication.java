package org.example.hbookstore;

import org.example.hbookstore.shared.securiry.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class HBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(HBookStoreApplication.class, args);
    }

}

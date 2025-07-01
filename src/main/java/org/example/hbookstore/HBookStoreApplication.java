package org.example.hbookstore;

import org.example.hbookstore.shared.securiry.RsaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaProperties.class)
public class HBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(HBookStoreApplication.class, args);
    }

}
